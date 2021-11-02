package etool.cdimc.models;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class ColumnModel {
    static final private int NUMMARK = 10;
    static final private char LFEED = '\n';
    static final private char COMMENT = '#';

    private final char separator = '$';
    private final ArrayList<String> fields;
    private boolean eofSeen;
    private final Reader in;


    public ColumnModel(InputStream input) throws IOException {
        this.fields = new ArrayList<>();
        this.eofSeen = false;
        this.in = new BufferedReader(stripBom(input));
    }

    static public Reader stripBom(InputStream in) {
        PushbackInputStream pin = new PushbackInputStream(in, 3);
        return new InputStreamReader(pin, StandardCharsets.UTF_8);
    }

    public boolean hasNext() throws IOException {
        if ( eofSeen ) return false;
        fields.clear();
        eofSeen = split( in, fields );
        if ( eofSeen ) return ! fields.isEmpty();
        else return true;
    }

    public List<String> next() {
        return fields;
    }

    static private boolean discardLinefeed(Reader in) throws IOException {
        in.mark(NUMMARK);
        int value = in.read();
        while ( value != -1 ) {
            char c = (char)value;
            if ( c != LFEED ) {
                in.reset();
                return false;
            } else {
                in.mark(NUMMARK);
                value = in.read();
            }
        }
        return true;
    }

    private boolean split(Reader in,ArrayList<String> fields) throws IOException {
        StringBuilder sbuf = new StringBuilder();
        int value;
        while ( (value = in.read()) != -1 ) {
            char c = (char)value;
            if (c == LFEED) {
                if (sbuf.length() > 0) {
                    fields.add(sbuf.toString());
                    sbuf.delete(0, sbuf.length());
                }
                return discardLinefeed(in);
            } else {
                if (c == separator) {
                    fields.add(sbuf.toString());
                    sbuf.delete(0, sbuf.length());
                } else {
                    if (c == COMMENT && fields.isEmpty() &&
                            sbuf.toString().trim().isEmpty()) {
                        sbuf.delete(0, sbuf.length());
                    } else sbuf.append(c);
                }
            }
        }
        if ( sbuf.length() > 0 ) {
            fields.add( sbuf.toString() );
            sbuf.delete( 0, sbuf.length() );
        }
        return true;
    }
}