package rs.ac.bg.etf.pp1;

import java.io.File;

import org.apache.log4j.Logger;
import org.apache.log4j.xml.DOMConfigurator;

import rs.ac.bg.etf.pp1.util.Log4JUtils;

public class MJEverythingTest {

	private static final Logger LOG = Logger.getLogger(MJEverythingTest.class);

	static {
		DOMConfigurator.configure(Log4JUtils.instance().findLoggerConfigFile());
		Log4JUtils.instance().prepareLogFile(Logger.getRootLogger());
	}

	public static void main(String[] args) {
		int exitCode = run(args);
		if (exitCode != 0) {
			System.exit(exitCode);
		}
	}

	static int run(String[] args) {
		if (args.length != 1) {
			System.err.println("Usage: MJEverythingTest <source.mj>");
			System.err.println(
				"Example: MJEverythingTest test\\test301.mj"
				+ " > test\\test301.out 2> test\\test301.err"
			);
			return 2;
		}

		String sourceFileName = args[0];
		try {
			File objectFile =
				MJCodeGeneratorTest.compile(sourceFileName, LOG);
			System.out.println(
				"Compilation succeeded: " + sourceFileName
			);
			System.out.println(
				"Object code written to: " + objectFile.getPath()
			);
			return 0;
		} catch (Exception exception) {
			LOG.debug(
				"Compilation failed for " + sourceFileName,
				exception
			);
			System.err.println(
				"Compilation failed for " + sourceFileName
				+ ": " + exception.getMessage()
			);
			return 1;
		}
	}
}
