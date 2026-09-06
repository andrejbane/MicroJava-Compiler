import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.Reader;

import java_cup.runtime.Symbol;

import rs.ac.bg.etf.pp1.MJParser;
import rs.ac.bg.etf.pp1.Yylex;
import rs.ac.bg.etf.pp1.SemanticPass;
import rs.ac.bg.etf.pp1.CodeGenerator;
import rs.ac.bg.etf.pp1.ast.Program;
import rs.etf.pp1.mj.runtime.Code;

public class Drv {
	public static void main(String[] args) throws Exception {
		String src = args[0];
		String obj = args[1];
		try (Reader reader = new BufferedReader(new FileReader(new File(src)))) {
			MJParser parser = new MJParser(new Yylex(reader));
			Symbol result = parser.parse();
			if (result == null || !(result.value instanceof Program)) {
				System.out.println("PARSE_FAIL");
				return;
			}
			Program program = (Program) result.value;
			SemanticPass sem = new SemanticPass();
			program.traverseBottomUp(sem);
			if (!sem.passed()) {
				System.out.println("SEMANTIC_FAIL");
				return;
			}
			CodeGenerator cg = new CodeGenerator();
			program.traverseBottomUp(cg);
			if (Code.greska) {
				System.out.println("CODEGEN_FAIL");
				return;
			}
			Code.write(new FileOutputStream(new File(obj)));
			System.out.println("OK mainPc=" + Code.mainPc + " dataSize=" + Code.dataSize + " pc=" + Code.pc);
		}
	}
}
