package etool.cdimc.models;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class CsvModel {
    static final private int NUMMARK = 10;
    static final private char CRETURN = '\r';
    static final private char LFEED = '\n';
    static final private char COMMENT = '#';

    private final boolean stripMultipleNewlines;

    private final char separator;
    private final ArrayList<String> fields;
    private boolean eofSeen;
    private final Reader in;

    static public Reader stripBom(InputStream in) {
        PushbackInputStream pin = new PushbackInputStream(in, 3);
        return new InputStreamReader(pin, StandardCharsets.UTF_8);
    }

    public CsvModel(boolean stripMultipleNewlines, char separator, InputStream input) throws IOException {
        this.stripMultipleNewlines = stripMultipleNewlines;
        this.separator = separator;
        this.fields = new ArrayList<>();
        this.eofSeen = false;
        this.in = new BufferedReader(stripBom(input));
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

    static private boolean discardLinefeed(Reader in, boolean stripMultiple) throws IOException {
        if ( stripMultiple ) {
            in.mark(NUMMARK);
            int value = in.read();
            while ( value != -1 ) {
                char c = (char)value;
                if ( c != CRETURN && c != LFEED ) {
                    in.reset();
                    return false;
                } else {
                    in.mark(NUMMARK);
                    value = in.read();
                }
            }
            return true;
        } else {
            in.mark(NUMMARK);
            int value = in.read();
            if ( value == -1 ) return true;
            else if ( (char)value != LFEED ) in.reset();
            return false;
        }
    }

    private boolean split(Reader in,ArrayList<String> fields) throws IOException {
        StringBuilder sbuf = new StringBuilder();
        int value;
        while ( (value = in.read()) != -1 ) {
            char c = (char)value;
            switch(c) {
                case CRETURN:
                    if ( sbuf.length() > 0 ) {
                        fields.add( sbuf.toString() );
                        sbuf.delete( 0, sbuf.length() );
                    }
                    return discardLinefeed( in, stripMultipleNewlines );

                case LFEED:
                    if ( sbuf.length() > 0 ) {
                        fields.add( sbuf.toString() );
                        sbuf.delete( 0, sbuf.length() );
                    }
                    return discardLinefeed( in, true);

                default:
                    if ( c == separator ) {
                        fields.add( sbuf.toString() );
                        sbuf.delete(0, sbuf.length());
                    }
                    else {
                        if ( c == COMMENT && fields.isEmpty() &&
                                sbuf.toString().trim().isEmpty() ) {
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