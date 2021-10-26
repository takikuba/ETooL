package etool.cdimc.models;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class TxtModel {
    static final private int NUMMARK = 2;
    static final private char CRETURN = '\r';
    static final private char LFEED = '\n';
    static final private char COMMENT = '#';

    private final boolean stripMultipleNewlines;

    private final char separator;
    private final ArrayList<String> fields = new ArrayList<>();
    private boolean eofSeen = false;
    private final Reader in;

    public TxtModel(boolean stripMultipleNewlines, char separator, InputStream input) throws IOException {
        this.stripMultipleNewlines = stripMultipleNewlines;
        this.separator = separator;
        this.in = new BufferedReader(stripBom(input));
    }

    public TxtModel(InputStream input, char separator) throws IOException {
        this.stripMultipleNewlines = true;
        this.separator = separator;
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

    static private Reader stripBom(InputStream in) {
        PushbackInputStream pin = new PushbackInputStream(in, 3);
        return new InputStreamReader(pin, StandardCharsets.UTF_8);
    }

    static private boolean discardLinefeed(Reader in, boolean stripMultiple) throws IOException {
        if (stripMultiple) {
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
        StringBuilder stringBuilder = new StringBuilder();
        int value;
        while ( (value = in.read()) != -1 ) {
            char c = (char)value;
            switch(c) {
                case CRETURN:
                    if ( stringBuilder.length() > 0 ) {
                        addIfExist(fields, stringBuilder);
                    }
                    return discardLinefeed( in, stripMultipleNewlines );

                case LFEED:
                    if ( stringBuilder.length() > 0 ) {
                        addIfExist(fields, stringBuilder);
                    }
                    return discardLinefeed( in, true);

                default:
                    if ( c == separator ) {
                        addIfExist(fields, stringBuilder);
                    }
                    else {
                        if ( c == COMMENT && fields.isEmpty() &&
                                stringBuilder.toString().trim().isEmpty() ) {
                            stringBuilder.delete(0, stringBuilder.length());
                        } else stringBuilder.append(c);
                    }
            }
        }
        addIfExist(fields, stringBuilder);
        return true;
    }

    private void addIfExist(ArrayList<String> fields, StringBuilder stringBuilder){
        if (stringBuilder.length() > 0) {
            fields.add(stringBuilder.toString());
            stringBuilder.delete(0, stringBuilder.length());
        }
    }
}