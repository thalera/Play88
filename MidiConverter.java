import java.io.*;
import javax.sound.midi.*;
import java.util.*;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.Dimension;

/**
 *	Aidan	Thaler
 *	May 18, 2019
 *
 *	Converts	any .mid	files	into files playable by PlayThatTune. Saves the files
 *	into the	./play folder for	immediate use if in the	same folder	as	PlayThatTune.
 */
public class MidiConverter	{

	/**
	 *	Midi code for a note	on	event.
	 */
   public static final int	NOTE_ON = 0x90;
	
	/**
	 *	Midi code for a tempo change event.
	 */
   public static final int	TEMPO_CHANGE =	0x51;
	
	/**
	 *	The offset value of midi keys	(they	start	at	21).
	 */
   public static final int	MIDI_OFFSET	= 21;
	
   public static void main(String[]	args)	{
      JFileChooser chooser	= new	JFileChooser(".");
      FileNameExtensionFilter	filter =	new FileNameExtensionFilter(
         "MIDI Files", "mid", "midi");
      chooser.setFileFilter(filter);
      chooser.setAcceptAllFileFilterUsed(false);
      chooser.setMultiSelectionEnabled(true);
      chooser.setPreferredSize(new Dimension(800, 600));
      int result = chooser.showOpenDialog(null);
      if	(result == JFileChooser.APPROVE_OPTION) {
         File[] midiFiles = chooser.getSelectedFiles();
         int invalids =	0;
         List<String> invalidNames = new ArrayList<>();
         for (File midiFile :	midiFiles) {
            try {
               System.out.println("Converting: " +	midiFile.getName());
               convert(midiFile);
               System.out.println("SUCCESSFUL");
            } catch (Exception e) {
               invalidNames.add(midiFile.exists() ? midiFile.getName() :
                                "***MISSING FILE***");
               System.out.println("***INVALID FILE***");
               System.out.println(e);
               invalids++;
            }	
         }
         System.out.println(invalids +	" invalid or missing file(s).");
         System.out.println("Invalid files: " + invalidNames);
      }
   }
	
	/**
	 *	Converts	the midiFile into	a txt	file and	saves	it	in	the ./play
	 *	directory.
	 *
	 *	@param midiFile the .mid or .midi file	to	convert.
	 *	@throws IllegalArgumentException if the file doesn't exist.
	 */
   public static void convert(File midiFile)	throws InvalidMidiDataException,
   																 FileNotFoundException, IOException {
      if	(!midiFile.exists())	throw	new IllegalArgumentException();
      // init
      Sequence	midiSeq = MidiSystem.getSequence(midiFile);
      int ppq = midiSeq.getResolution();
      SortedMap<Long, Double>	tempos =	new TreeMap<>();
      SortedMap<Long, List<GuitarEvent>> events	= new	TreeMap<>();
      // get info
      getTemposAndEvents(tempos,	events, midiSeq);
      for (Long tick : events.keySet()) {
         transpose(events.get(tick));
      }
      computeDurations(ppq, tempos,	events);
      //	output
      File playFolder = new File("./play");
      playFolder.mkdir();
      String outName	= midiFile.getName();
      int typeIndex = outName.toLowerCase().indexOf(".mid");
      outName = outName.substring(0, typeIndex)	+ ".txt";
      PrintStream	output =	new PrintStream(new File("./play/" + outName));
      for (Long tick	: events.keySet()) {
         for (GuitarEvent event : events.get(tick)) {
            output.println(event.key +	" " +	event.duration);
         }
      }
   }
	
	/**
	 *	Goes through the midiSeq and sorts tempo changes and GuitarEvents
	 *	into the	maps.	Initializes	all events to 0 duration. If there are no
    * tempos, defaults to 120 bpm. Assumes values passed are non-null
	 *
	 *	@param tempos a map from ticks to tempos at that tick
	 *	@param events a map from ticks to lists of GuitarEvents.
	 *	@param midiSeq	the sequence to pull	from.
	 */
   public static void getTemposAndEvents(SortedMap<Long,	Double> tempos, 
   									SortedMap<Long, List<GuitarEvent>> events, 
   									Sequence	midiSeq)	{
      for (Track track : midiSeq.getTracks()) {
         for (int	i = 0; i	< track.size(); i++)	{
            MidiEvent midEvent =	track.get(i);
            MidiMessage	msg =	midEvent.getMessage();
            if	(msg instanceof MetaMessage && 
            		((MetaMessage)	msg).getType()	==	TEMPO_CHANGE) {
               byte[] data	= ((MetaMessage) msg).getData();
            	//	convert data into	a double
               double tempo =	(data[0]	& 0xff) << 16 | (data[1] &	0xff)	<<	8 |
                  				(data[2]	& 0xff);
            	//	this converts the	tempo	into bpm
               tempo	= 60 / (tempo / 1000000);
               tempos.put(midEvent.getTick(), tempo);
            } else if (msg	instanceof ShortMessage) {
               ShortMessage shortMsg =	(ShortMessage)	msg;
            	//	if	the key is between 0	and 88
               if	(shortMsg.getCommand() == NOTE_ON &&
               	  shortMsg.getData1() >= MIDI_OFFSET &&
               	  shortMsg.getData1() <= 87 +	MIDI_OFFSET	&&
               	  shortMsg.getData2() != 0) {
               	//	get key
                  int key = shortMsg.getData1()	- MIDI_OFFSET;
                  long tick =	midEvent.getTick();
               	//	init list
                  if	(!events.containsKey(tick))	{
                     events.put(tick, new	LinkedList<>());
                  }
               	//	create event and add	to	map
                  GuitarEvent	newEvent	= new	GuitarEvent(key, 0);
                  events.get(tick).add(newEvent);
               }
            }
         }
         if (tempos.isEmpty()) { // if there are no tempos, default to 120.
            tempos.put((long) 0, 120.0);
         }
      }
   }
	
	/**
	 *	Computes	and updates	the duration of each	event	in	the events,	using
	 *	the tempos and	ppq to compute	time. Assumes there is at least one tempo.
	 *
	 *	@param ppq the	pulses per quarter note	of	the sequence.
	 *	@param tempos ticks to tempos	in	bpm.
	 *	@param events ticks to GuitarEvents, with	the events to update.
	 */
   public static void computeDurations(int ppq,	SortedMap<Long, Double>	tempos, 
   									 SortedMap<Long, List<GuitarEvent>>	events) {
      // init
      double prevTime =	0;
      Iterator<Long>	tempoTicksItr = tempos.keySet().iterator();
      double currTempo = tempos.get(tempoTicksItr.next());
      long nextTempoChange	= -1;
      if (tempoTicksItr.hasNext()) {
         nextTempoChange =	tempoTicksItr.next();
      }
      GuitarEvent	prevEvent =	null;	//	so	we	can hold	on	to	the last	one
      for (long tick	: events.keySet()) {
         double newTime	= tick *	(60.0	/ (currTempo *	ppq));
         for (GuitarEvent event : events.get(tick)) {
            // compute time
            if	(prevEvent != null) {
               prevEvent.duration =	newTime - prevTime;
               prevTime	= newTime;
            }
            // update event
            prevEvent =	event;
         }
         // update tempo change
         if	(tick	>=	nextTempoChange && nextTempoChange != -1)	{
            currTempo =	tempos.get(nextTempoChange);
            if	(tempoTicksItr.hasNext()) {
               nextTempoChange =	tempoTicksItr.next();
            } else {
               nextTempoChange =	-1;
            }
            // update time to use new tempo
            prevTime	= tick *	(60.0	/ (currTempo *	ppq));
         }
      }
   	//	set last	note duration arbitrarily
      prevEvent.duration =	1.5;
   }
   
   /**
    * Transposes the events in the list down into octaves playable by
    * Guitar37. Keeps the distances between the notes the same as much
    * as it can.
    *
    * @param events the notes to transpose.
    */
   private static void transpose(List<GuitarEvent> events) {
      // find max/min keys
      int minKey = 87;
      int maxKey = 0;
      for (GuitarEvent note : events) {
         minKey = Math.min(note.key, minKey);
         maxKey = Math.max(note.key, maxKey);
      }
      // find octaves to transpose by
      int downTransposeBy = (int) Math.ceil(((maxKey - 60.0) / 12)); // for notes >= middle C
      int upTransposeBy = (int) Math.ceil(((24.0 - minKey) / 12)); // for notes < middle C
      // do the tranpose
      for (GuitarEvent note : events) {
         if (note.key >= 39) {
            note.key -= 12 * downTransposeBy;
         } else {
            note.key += 12 * upTransposeBy;
         }
      }
      // make sure no notes went out of bounds
      for (GuitarEvent note : events) {
         while (note.key > 60) note.key -= 12;
         while (note.key < 24) note.key += 12;
         // transpose key to pitch
         note.key -= 48;
      }
   }
	
	/**
	 *	A single guitar event - a key and a duration.
	 */
   private static	class	GuitarEvent	{
   	
   	/**
   	 *	The key of the	event.
   	 */
      public int key;
   
   	/**
   	 *	The duration in seconds	of	the event.
   	 */
      public double duration;
   
   	/**
   	 *	Constructs a new GuitarEvent with the data.
   	 *
   	 *	@param key the	key of the event.
   	 *	@param duration the duration in seconds before the	next event.
   	 */
      public GuitarEvent(int key, double duration)	{
         this.key	= key;
         this.duration = duration;
      }
   }
	
}