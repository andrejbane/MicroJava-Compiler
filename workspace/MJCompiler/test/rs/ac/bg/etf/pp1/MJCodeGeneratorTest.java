package rs.ac.bg.etf.pp1;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.Reader;

import java_cup.runtime.Symbol;

import org.apache.log4j.Logger;
import org.apache.log4j.xml.DOMConfigurator;

import rs.ac.bg.etf.pp1.ast.Program;
import rs.ac.bg.etf.pp1.util.Log4JUtils;
import rs.etf.pp1.mj.runtime.Code;

public class MJCodeGeneratorTest {

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
		Logger log = Logger.getLogger(MJCodeGeneratorTest.class);
		String[] sourceFiles = args.length == 0 ? TEST_FILES : args;
		Exception firstFailure = null;

		for (String sourceFile : sourceFiles) {
			try {
				compile(sourceFile, log);
			} catch (Exception exception) {
				log.error("Compilation failed for " + sourceFile, exception);
				if (firstFailure == null) {
					firstFailure = exception;
				}
			}
		}

		if (firstFailure != null) {
			throw firstFailure;
		}
	}

	static File compile(String sourceFileName, Logger log)
		throws Exception {
		File sourceFile = new File(sourceFileName);
		File objectFile = objectFileFor(sourceFile);
		if (objectFile.exists() && !objectFile.delete()) {
			throw new IllegalStateException(
				"Cannot remove stale object file " + objectFile.getAbsolutePath()
			);
		}
		log.info("Compiling source file: " + sourceFile.getAbsolutePath());

		try (Reader reader =
			new BufferedReader(new FileReader(sourceFile))) {
			Yylex lexer = new Yylex(reader);
			MJParser parser = new MJParser(lexer);
			Symbol result = parser.parse();
			if (lexer.hasErrors()) {
				throw new IllegalStateException(
					"Lexical errors detected in " + sourceFile.getName()
				);
			}
			if (parser.hasErrors()) {
				throw new IllegalStateException(
					"Syntax errors detected in " + sourceFile.getName()
				);
			}
			if (result == null || !(result.value instanceof Program)) {
				throw new IllegalStateException(
					"Parser did not produce a Program syntax tree"
				);
			}

			Program program = (Program) result.value;
			SemanticPass semanticPass = new SemanticPass();
			program.traverseBottomUp(semanticPass);
			if (!semanticPass.passed()) {
				throw new IllegalStateException(
					"Semantic errors detected in " + sourceFile.getName()
				);
			}

			CodeGenerator codeGenerator = new CodeGenerator();
			program.traverseBottomUp(codeGenerator);
			if (Code.greska) {
				throw new IllegalStateException(
					"Code generation failed for " + sourceFile.getName()
				);
			}

			try (FileOutputStream output = new FileOutputStream(objectFile)) {
				Code.write(output);
			}
			log.info("Object code written to " + objectFile.getAbsolutePath());
		}
		log.info("===================================");
		return objectFile;
	}

	private static File objectFileFor(File sourceFile) {
		String name = sourceFile.getName();
		int extension = name.lastIndexOf('.');
		String baseName = extension < 0 ? name : name.substring(0, extension);
		return new File(sourceFile.getParentFile(), baseName + ".obj");
	}
}
