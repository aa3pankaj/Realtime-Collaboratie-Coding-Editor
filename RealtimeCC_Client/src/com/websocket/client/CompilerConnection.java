package com.websocket.client;
import com.hackerearth.heapi.sdk.responses.CompileResponse;
import com.hackerearth.heapi.sdk.responses.RunResponse;

public class CompilerConnection {

	public static CompileResponse compile() {

		TestCompile test = new TestCompile();

		return test.TestCompileRequest();

	}

	public static RunResponse run() {

		TestCompile test = new TestCompile();

		return test.TestRunRequest();

	}

}
