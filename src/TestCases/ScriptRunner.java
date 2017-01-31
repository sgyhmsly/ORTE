package TestCases;

/*
 * Slightly modified version of the com.ibatis.common.jdbc.ScriptRunner class
 * from the iBATIS Apache project. Only removed dependency on Resource class
 * and a constructor
 * GPSHansl, 06.08.2015: regex for delimiter, rearrange comment/delimiter detection, remove some ide warnings.
 */
/*
 *  Copyright 2004 Clinton Begin
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */

import java.io.*;
import java.sql.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Tool to run database scripts
 */
public class ScriptRunner {

    private static final String DEFAULT_DELIMITER = ";";
    /**
     * regex to detect delimiter.
     * ignores spaces, allows delimiter in comment, allows an equals-sign
     */
    public static final Pattern delimP = Pattern.compile("^\\s*(--)?\\s*delimiter\\s*=?\\s*([^\\s]+)+\\s*.*$", Pattern.CASE_INSENSITIVE);

    private final Connection connection;

    private final boolean stopOnError;
    private final boolean autoCommit;

//    @SuppressWarnings("UseOfSystemOutOrSystemErr")
//    private PrintWriter logWriter = new PrintWriter(System.out);
//    @SuppressWarnings("UseOfSystemOutOrSystemErr")
//    private PrintWriter errorLogWriter = new PrintWriter(System.err);

    private String delimiter = DEFAULT_DELIMITER;
    private boolean fullLineDelimiter = false;
    private final File actualOutputFile;
    private final BufferedWriter actualOutputWriter;
    /**
     * Default constructor
     */
    public ScriptRunner(Connection connection, final boolean autoCommit,
                        final boolean stopOnError,final File actualOutputFile)throws IOException
    {
        this.connection = connection;
        this.autoCommit = autoCommit;
        this.stopOnError = stopOnError;
        this.actualOutputFile = actualOutputFile;
        this.actualOutputFile.createNewFile();
        actualOutputWriter = new BufferedWriter(new FileWriter(this.actualOutputFile));
    }

    public void setDelimiter(final String delimiter, final boolean fullLineDelimiter) {
        this.delimiter = delimiter;
        this.fullLineDelimiter = fullLineDelimiter;
    }

    /**
     * Setter for logWriter property
     *
     * @param logWriter - the new value of the logWriter property
     */
//    public void setLogWriter(final PrintWriter logWriter) {
//        this.logWriter = logWriter;
//    }

    /**
     * Setter for errorLogWriter property
     *
     * @param errorLogWriter - the new value of the errorLogWriter property
     */
//    public void setErrorLogWriter(final PrintWriter errorLogWriter) {
//        this.errorLogWriter = errorLogWriter;
//    }

    /**
     * Runs an SQL script (read in using the Reader parameter)
     *
     * @param reader - the source of the script
     */
    public void runScript(final Reader reader) throws IOException, SQLException {
        try {
            final boolean originalAutoCommit = connection.getAutoCommit();
            try {
                if (originalAutoCommit != this.autoCommit) {
                    connection.setAutoCommit(this.autoCommit);
                }
                runScript(connection, reader);
            } finally {
                connection.setAutoCommit(originalAutoCommit);
            }
        } catch (IOException | SQLException e) {
            throw e;
        }
    }

    /**
     * Runs an SQL script (read in using the Reader parameter) using the
     * connection passed in
     *
     * @param conn - the connection to use for the script
     * @param reader - the source of the script
     * @throws SQLException if any SQL errors occur
     * @throws IOException if there is an error reading from the Reader
     */
    private void runScript(final Connection conn, final Reader reader) throws IOException,
            SQLException {
        StringBuffer command = null;
        try {
            final LineNumberReader lineReader = new LineNumberReader(reader);
            String line;
            while ((line = lineReader.readLine()) != null) {
                if (command == null) {
                    command = new StringBuffer();
                }
                final String trimmedLine = line.trim();
                final Matcher delimMatch = delimP.matcher(trimmedLine);
                if (trimmedLine.length() < 1
                        || trimmedLine.startsWith("//")) {
                    // Do nothing
                } else if (delimMatch.matches()) {
                    setDelimiter(delimMatch.group(2), false);
                } else if (trimmedLine.startsWith("--")) {
                    //println(trimmedLine);
                } else if (trimmedLine.length() < 1
                        || trimmedLine.startsWith("--")) {
                    // Do nothing
                } else if (!fullLineDelimiter
                        && trimmedLine.endsWith(getDelimiter())
                        || fullLineDelimiter
                        && trimmedLine.equals(getDelimiter())) {
                    command.append(line.substring(0, line
                            .lastIndexOf(getDelimiter())));
                    command.append(" ");
                    this.execCommand(conn, command, lineReader);
                    command = null;
                } else {
                    command.append(line);
                    command.append("\n");
                }
            }
            if (command != null) {
                this.execCommand(conn, command, lineReader);
            }
            if (!autoCommit) {
                conn.commit();
            }
        } catch (Exception e) {
            throw new IOException(String.format("Error executing '%s': %s", command, e.getMessage()), e);
        } finally {
            //conn.rollback();
            flush();
        }
    }

    private void execCommand(final Connection conn, final StringBuffer command,
                             final LineNumberReader lineReader) throws SQLException,IOException {
        Statement statement= null;
        try
        {
            statement = conn.createStatement();
            //println(command);
            boolean hasResults = false;
            hasResults = statement.execute(command.toString());
            if (autoCommit && !conn.getAutoCommit()) {
                conn.commit();
            }



            ResultSet rs = statement.getResultSet();
            if (hasResults && rs != null) {
                ResultSetMetaData md = rs.getMetaData();
                int cols = md.getColumnCount();
                for (int i = 1; i <= cols; i++) {
                    String name = md.getColumnLabel(i);
                    //println(name + "\t");
                }
                //println("");
                while (rs.next()) {
                    for (int i = 1; i <= cols; i++) {
                        String value = rs.getString(i);
                        println(value);
                    }
                }
            }
        }
        catch (SQLException e) {
            final String errText = String.format("Error executing '%s' (line %d): %s", command, lineReader.getLineNumber(), e.getMessage());
            if (stopOnError) {
                throw new SQLException(errText, e);
            } else {
                //println(errText);
            }
        }


        finally
        {
            try {
                if (statement!=null)
                    statement.close();
            }
            catch (SQLException e)
            {
                throw e;
            }
//            finally
//            {
//                connection.close();
//            }
        }



    }


    private String getDelimiter() {
        return delimiter;
    }

    @SuppressWarnings("UseOfSystemOutOrSystemErr")
    private void print(final Object o) {
        ;
    }

    private void println(final Object o)throws IOException
    {
        if (actualOutputWriter !=null)
        {
            actualOutputWriter.write(o.toString());
            actualOutputWriter.newLine();
        }
    }

    private void printlnError(final Object o) {
//        if (errorLogWriter != null) {
//            errorLogWriter.println(o);
//        }
        ;
    }

    private void flush()throws IOException {
        if (actualOutputWriter != null) {
            actualOutputWriter.flush();
            actualOutputWriter.close();
        }
//        if (errorLogWriter != null) {
//            errorLogWriter.flush();
//        }
    }
}
