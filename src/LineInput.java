/**
 * Deals with Passing around data for conversion from front end to the back end
 * @param text the text that will be converted to song
 * @param BPM preset for initial song Bpm
 * @param volume preset for initial volume
 * @param instrument preset for initial instrument
 * @param octave preset for initial octave
 */
public record LineInput(String text, int BPM, int volume, int instrument, int octave) {}
