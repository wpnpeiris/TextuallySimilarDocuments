package com.wpnpeiris.datamining.similardocuments;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class Test {

	public static void main(String[] args) throws IOException {
		Files.list(Paths.get("dataset/space")).forEach(f -> {
			File fileToMove = new File(f.toString());
			fileToMove.renameTo(new File(f.toString() + ".txt"));
		});

	}

}
