package rs.ac.bg.etf.pp1;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;

import java_cup.runtime.Symbol;

import org.apache.log4j.Logger;
import org.apache.log4j.xml.DOMConfigurator;

import rs.ac.bg.etf.pp1.util.Log4JUtils;

public class MJTest {

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
	
	public static void main(String[] args) throws IOException {
		Logger log = Logger.getLogger(MJTest.class);

		IOException firstFailure = null;
		for (String testFile : TEST_FILES) {
			try {
				runLexer(testFile, log);
			} catch (IOException exception) {
				log.error("Lexer test failed for " + testFile, exception);
				if (firstFailure == null) {
					firstFailure = exception;
				}
			}
		}

		if (firstFailure != null) {
			throw firstFailure;
		}
	}

	private static void runLexer(String testFile, Logger log) throws IOException {
		File sourceCode = new File(testFile);
		log.info("Lexing source file: " + sourceCode.getAbsolutePath());

		try (Reader reader = new BufferedReader(new FileReader(sourceCode))) {
			Yylex lexer = new Yylex(reader);
			Symbol currentToken;
			while ((currentToken = lexer.next_token()).sym != sym.EOF) {
				if (currentToken.value != null) {
					log.info(currentToken.toString() + " " + currentToken.value.toString());
				}
			}
		}
		log.info("===================================");
	}
	
}