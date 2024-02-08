package rs.ac.bg.etf.pp1;

import java.io.*;

import java_cup.runtime.Symbol;

import org.apache.log4j.Logger;
import org.apache.log4j.xml.DOMConfigurator;

import rs.ac.bg.etf.pp1.ast.Program;
import rs.ac.bg.etf.pp1.util.Log4JUtils;
import rs.etf.pp1.mj.runtime.Code;
import rs.etf.pp1.symboltable.Tab;
import rs.etf.pp1.symboltable.concepts.Obj;
import rs.etf.pp1.symboltable.concepts.Struct;

public class MJParserTest {

	static {
		DOMConfigurator.configure(Log4JUtils.instance().findLoggerConfigFile());
		Log4JUtils.instance().prepareLogFile(Logger.getRootLogger());
	}

	public static void run_test(String filename)  throws Exception{
		Logger log = Logger.getLogger(MJParserTest.class);

		Reader br = null;

		File sourceCode = new File("test/"+filename+".mj");
		log.info("Compiling source file: " + sourceCode.getAbsolutePath());

		br = new BufferedReader(new FileReader(sourceCode));
		Yylex lexer = new Yylex(br);

		MJParser p = new MJParser(lexer);
		Symbol s = p.parse();  //pocetak parsiranja

		Program prog = (Program)(s.value);
		Tab.init();
		Tab.currentScope.addToLocals(new Obj(Obj.Type, "bool", new Struct(Struct.Bool)));

		// ispis sintaksnog stabla
		log.info(prog.toString(""));
		log.info("===================================");

		// ispis prepoznatih programskih konstrukcija
		SemanticAnalyzer v = new SemanticAnalyzer();
		prog.traverseBottomUp(v);

		log.info("===================================");
		Tab.dump();

		if(!v.util.errorDetected){File objFile = new File("test/"+filename+"out.obj");
			if(objFile.exists()) objFile.delete();

			CodeGenerator codeGenerator = new CodeGenerator();
			codeGenerator.util.staticVarNum=v.nVars;

			prog.traverseBottomUp(codeGenerator);
			Code.dataSize = codeGenerator.util.staticVarNum;
			Code.mainPc = codeGenerator.getMainPc();
			Code.write(new FileOutputStream(objFile));
			log.info("Parsiranje uspesno zavrseno!");

		}else{
			log.error("Parsiranje NIJE uspesno zavrseno!");
		}

	}

	public static void main(String[] args)  throws Exception{
		run_test("test301");
		run_test("test302");
		run_test("test303");
//		run_test("test");
	}

}
