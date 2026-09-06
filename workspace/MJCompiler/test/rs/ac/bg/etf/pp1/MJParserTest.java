package rs.ac.bg.etf.pp1;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;

import java_cup.runtime.Symbol;

import org.apache.log4j.Logger;
import org.apache.log4j.xml.DOMConfigurator;

import rs.ac.bg.etf.pp1.ast.Program;
import rs.ac.bg.etf.pp1.util.Log4JUtils;

public class MJParserTest {

	private static final String[] TEST_FILES = {
		"test/test301.mj",
		"test/test302.mj",
		"test/test303.mj",
		"test/test304.mj"
	};

	static {
		DOMConfigurator.configure(Log4JUtils.instance().findLoggerConfigFile());
		Log4JUtils.instance().prepareLogFile(Logger.getRootLogger());
	}
	
	public static void main(String[] args) throws Exception {
		Logger log = Logger.getLogger(MJParserTest.class);

		Exception firstFailure = null;
		for (String testFile : TEST_FILES) {
			try {
				runParser(testFile, log);
			} catch (Exception exception) {
				log.error("Parser test failed for " + testFile, exception);
				if (firstFailure == null) {
					firstFailure = exception;
				}
			}
		}

		if (firstFailure != null) {
			throw firstFailure;
		}
	}

	private static void runParser(String testFile, Logger log) throws Exception {
		File sourceCode = new File(testFile);
		log.info("Parsing source file: " + sourceCode.getAbsolutePath());

		try (Reader reader = new BufferedReader(new FileReader(sourceCode))) {
			MJParser parser = new MJParser(new Yylex(reader));
			Symbol result = parser.parse();
			if (result == null || !(result.value instanceof Program)) {
				throw new IllegalStateException("Parser did not produce a Program syntax tree");
			}

			Program program = (Program) result.value;
			log.info(program.toString(""));
			log.info("Syntax errors detected: " + parser.hasErrors());
		}
		log.info("===================================");
	}
}
