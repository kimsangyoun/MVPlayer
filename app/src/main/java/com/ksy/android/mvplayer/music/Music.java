package com.ksy.android.mvplayer.music;

import org.jaudiotagger.audio.AudioFileIO;
import org.jaudiotagger.audio.mp3.MP3File;
import org.jaudiotagger.tag.FieldKey;
import org.jaudiotagger.tag.id3.AbstractID3v2Tag;

import java.io.File;
import java.io.Serializable;

/**
 * Created by AppCreater01 on 2016-08-08.
 */
public class Music implements Serializable {
    public String sid; //Music identity id
    public String sname; // Music name?
    public String stitle; // Music title
    public String sgenre; // Music genre
    public String slength; // Music length
    public String sartist; // Music artist
    public String suri; // Music Uri
    public String salbumid; //Music albumId
    public String slyrics; // Music's Lyrics
    public int songlength;


    public Long getSid(){
        return Long.parseLong(sid);
    }
    public String getLyrics(){
        File file;
        file = new File( this.suri);
        try {
            MP3File mp3 = (MP3File) AudioFileIO.read(file);
            AbstractID3v2Tag tag2 = mp3.getID3v2Tag();
            this.slyrics = tag2.getFirst(FieldKey.LYRICS);

        }catch (Exception e){

        }
        return this.slyrics;
    }
}

