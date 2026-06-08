/**
 * Record responsible for storing the front end "line" object data
 * Each line has this one instance of this record for every save made
 * @param text
 * @param bpm
 * @param volume
 * @param instrument
 * @param octave
 */
public record LinePreset(String text, String bpm, String volume, String instrument,String octave) {}
