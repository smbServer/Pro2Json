package xyz.frish2021.pro2Json;

import com.google.gson.GsonBuilder;
import org.apache.commons.io.FileUtils;
import xyz.frish2021.pro2Json.utils.MethodUtils;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Pro2Json {
    private final File inFile;
    private final File outFile;

    public Pro2Json(File inFile, File outFile) {
        this.inFile = inFile;
        this.outFile = outFile;
    }

    public final static Map<String, Object> jsonAll = new HashMap<String, Object>();
    public final static Map<String, String> clazzObf2Clean = new HashMap<String, String>();
    public final static Map<String, String> fieldObf2Clean = new HashMap<String, String>();
    public final static Map<String, String> methodObf2Clean = new HashMap<String, String>();
    public final static ArrayList<String> clazzJsonMapping = new ArrayList<String>();
    public final static ArrayList<String> fieldJsonMapping = new ArrayList<String>();
    public final static ArrayList<String> methodJsonMapping = new ArrayList<String>();

    public void genJsonMapping() throws IOException {
        String text = Files.readString(inFile.toPath());
        String[] spilt = text.split("\\r\\n|\\n");

        String clean = null;
        String obf = null;

        for (String map : spilt) {
            if (map.startsWith("#")) {
                continue;
            }

            if (map.contains("->") && map.endsWith(":")) {
                String[] obf2Clean = map.trim().split("->");
                obf = obf2Clean[1].replace(":", "").trim();
                clean = obf2Clean[0].replace(".", "/").trim();

                clazzObf2Clean.put(obf, clean);
            }

            if (obf == null || clean == null) {
                continue;
            }

            if (map.contains("->") && !map.endsWith(":")) {
                if (!map.contains("(")) {
                    String[] obf2Clean = map.trim().split("( | ->)+");
                    String fieldObf = obf + "/" + obf2Clean[3];
                    String fieldClean = clean + "/" + obf2Clean[1];

                    fieldObf2Clean.put(fieldObf, fieldClean);
                } else {
                    String[] obf2Clean = map.contains(":") ? map.substring(map.lastIndexOf(":") + 1).trim().split("( |->)+") : map.trim().split("( |->)+");
                    String cleanReturn = MethodUtils.notPrimitive(obf2Clean[0]) ? "L" + MethodUtils.internalize(obf2Clean[0]) + ";" : MethodUtils.internalize(obf2Clean[0]);
                    String cleanName = obf2Clean[1].substring(0, obf2Clean[1].lastIndexOf("("));
                    String cleanArgs = obf2Clean[1].substring(obf2Clean[1].indexOf("(") + 1, obf2Clean[1].lastIndexOf(")"));
                    String obfReturn = MethodUtils.notPrimitive(obf2Clean[0]) ? "L" + clazzObf2Clean.getOrDefault(MethodUtils.internalize(obf2Clean[0]), MethodUtils.internalize(obf2Clean[0])) + ";" : cleanReturn;
                    String obfName = obf2Clean[2];
                    String obfArgs;


                    if (!cleanArgs.equals("")) {
                        StringBuilder tempCleanArs = new StringBuilder();
                        StringBuilder tempObfArs = new StringBuilder();
                        for (String s : cleanArgs.split(",")) {
                            if (MethodUtils.notPrimitive(s)) {
                                tempObfArs.append("L").append(clazzObf2Clean.getOrDefault(MethodUtils.internalize(s), MethodUtils.internalize(s))).append(";");
                                tempCleanArs.append("L").append(MethodUtils.internalize(s)).append(";");
                            } else {
                                tempObfArs.append(MethodUtils.internalize(s));
                                tempCleanArs.append(MethodUtils.internalize(s));
                            }
                        }
                        obfArgs = "(" + tempObfArs + ")";
                        cleanArgs = "(" + tempCleanArs + ")";
                    } else {
                        obfArgs = "()";
                        cleanArgs = "()";
                    }

                    String methodObf = obf + "." + obfName + " " + obfArgs + obfReturn;
                    String methodClean = clean + "." + cleanName + " " + cleanArgs + cleanReturn;

                    methodObf2Clean.put(methodObf, methodClean);
                }
            }
        }

        for (Map.Entry<String, String> stringStringEntry : clazzObf2Clean.entrySet()) {
            clazzJsonMapping.add(stringStringEntry.getKey() + " -> " + stringStringEntry.getValue());
        }

        for (Map.Entry<String, String> stringStringEntry : fieldObf2Clean.entrySet()) {
            fieldJsonMapping.add(stringStringEntry.getKey() + " -> " + stringStringEntry.getValue());
        }

        for (Map.Entry<String, String> stringStringEntry : methodObf2Clean.entrySet()) {
            methodJsonMapping.add(stringStringEntry.getKey() + " -> " + stringStringEntry.getValue());
        }

        jsonAll.put("class", clazzJsonMapping);
        jsonAll.put("field", fieldJsonMapping);
        jsonAll.put("method", methodJsonMapping);

        String json = new GsonBuilder().disableHtmlEscaping().setPrettyPrinting().create().toJson(jsonAll);
        FileUtils.writeStringToFile(outFile, json, "utf-8");
    }
}
