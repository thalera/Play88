# Play88
Based on Guitar37, a pretty-ok MIDI sequencer and synthesizer.

Simply run Play88.jar and select whatever midi file you want to play.

Supports time scrubbing and volume adjustments.

Simulates 4 (really more like 3 1/2) instruments - Guitar, Piano, Organ, and Bright Organ. Adjust the harmonics slider to change the number of harmonics being simulated. Essentially just makes it sound more bright. Does nothing for Guitar. May impact performance (although hopefully it runs well - uses some multithreading to make it FAST)

Also, I've included MidiConverter.java which can be used to convert MIDI files into .txt files playable by PlayThatTune.java. Simply put into the same folder as PlayThatTune.java, run, and select any MIDI files you want to convert.

(Also, no, you can't find solutions to Guitar37 here - the source files for the instruments are not included).
