package com.company;
import java.io.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static java.lang.System.exit;
import static java.lang.System.in;

public class Main {
    public static void main(String[] args) {

        // The name of the file to open.
        String fileName = "file.txt";

        // This will reference one line at a time
        String line = "";

        try {
            // FileReader reads text files in the default encoding.
            FileReader fileReader =
                    new FileReader(fileName);

            // Always wrap FileReader in BufferedReader.
            BufferedReader bufferedReader =
                    new BufferedReader(fileReader);

            int lineNumber = 0;
            int totalOpcodes = 0;
            int[] input = new int[4];
            int[] opcode = new int[4];
            int[] output = new int[4];
            while ((line = bufferedReader.readLine()) != null) {
                /*
                lines goes as follows
                    Before: [3, 2, 1, 1]
                    9 2 1 2
                    After:  [3, 2, 2, 1]
                 */
                Pattern p = Pattern.compile("-?[0-9]+");
                Matcher m = p.matcher(line);
                int i = 0;
                while (m.find()) {
                    if (lineNumber%4 == 0) {
                        input[i] = Integer.parseInt(m.group());
                    }
                    if (lineNumber%4 == 1) {
                        opcode[i] = Integer.parseInt(m.group());
                    }
                    if (lineNumber%4 == 2) {
                        output[i] = Integer.parseInt(m.group());
                    }
                    i++;
                }
                int opcodes = 0;
         //       System.out.println("input: " + input[0] + " " + input[1] + " " + input[2] + " " + input[3]);
                if (lineNumber%4 == 2) {
                    // go through opcodes
                    if (addr(input, output, opcode)) { opcodes++;
                    //System.out.println("addr");
                    }
                    if (addi(input, output, opcode)) { opcodes++;
                    //System.out.println("addi");
                        /*
                        System.out.println("input: " + input[0] + " " + input[1] + " " + input[2] + " " + input[3]);
                        System.out.println("output: " + output[0] + " " + output[1] + " " + output[2] + " " + output[3]);
                        System.out.println("opcode: " + opcode[0] + " " + opcode[1] + " " + opcode[2] + " " + opcode[3]);
                        int[] c = addi(input, opcode);
                        System.out.println("input addi: " + c[0] + " " + c[1] + " " + c[2] + " " + c[3]);
                        */
                    }
                    if (muli(input, output, opcode)) { opcodes++;
//                    System.out.println("muli");
                    }
                    if (mulr(input, output, opcode)) { opcodes++;
  //                  System.out.println("mulr");
                    }
                    if (bori(input, output, opcode)) { opcodes++;
    //                System.out.println("bori");
                    }
                    if (borr(input, output, opcode)) { opcodes++;
                    //System.out.println("borr");
                    }
                    if (bani(input, output, opcode)) { opcodes++;
                  //  System.out.println("bani");
                    }
                    if (banr(input, output, opcode)) { opcodes++;
                //    System.out.println("banr");
                    }
                    if (seti(input, output, opcode)) { opcodes++;
              //      System.out.println("seti");
                    }
                    if (setr(input, output, opcode)) { opcodes++;
            //        System.out.println("setr");
                    }
                    if (gtir(input, output, opcode)) { opcodes++;
          //          System.out.println("gtir");
                    }
                    if (gtri(input, output, opcode)) { opcodes++;
        //            System.out.println("gtri");
                    }
                    if (gtrr(input, output, opcode)) { opcodes++;
      //              System.out.println("gtrr");
                    }
                    if (eqir(input, output, opcode)) { opcodes++;
    //                System.out.println("eqir");
                    }
                    if (eqri(input, output, opcode)) { opcodes++;
  //                  System.out.println("eqri");
                    }
                    if (eqrr(input, output, opcode)) { opcodes++;
//                    System.out.println("eqrr");
                    }
                }
                if (opcodes >= 3) {
                    totalOpcodes++;
                }
   //             System.out.println("opcodes: " + opcodes);
                lineNumber++;
            }
            // Always close files.
            bufferedReader.close();

            System.out.println("Total opcodes: " + totalOpcodes);

        } catch (FileNotFoundException ex) {
            System.out.println(
                    "Unable to open file '" +
                            fileName + "'");
        } catch (IOException ex) {
            System.out.println(
                    "Error reading file '"
                            + fileName + "'");
            // Or we could just do this:
            // ex.printStackTrace();
        }
    }

    /*
    Addition:
addr (add register) stores into register C the result of adding register A and register B.
addi (add immediate) stores into register C the result of adding register A and value B.
*/
    public static boolean addr(int[] input, int[] output, int[] opcode) {
        if (input[opcode[1]]+input[opcode[2]] == output[opcode[3]]) {
            return true;
        } else {
            return false;
        }
    }

    public static boolean addi(int[] input, int[] output, int[] opcode) {
        if (input[opcode[1]]+opcode[2] == output[opcode[3]]) {
            return true;
        } else {
            return false;
        }
    }
    public static int[] addr(int[] input, int[] opcode) {
        input[opcode[3]] = input[opcode[1]]+input[opcode[2]];
        return input;
    }

    public static int[] addi(int[] input, int[] opcode) {
        input[opcode[3]] = input[opcode[1]]+opcode[2];
        return input;
    }


    /*
Multiplication:
mulr (multiply register) stores into register C the result of multiplying register A and register B.
muli (multiply immediate) stores into register C the result of multiplying register A and value B.
*/
    public static boolean mulr(int[] input, int[] output, int[] opcode) {
 //       System.out.println("opcode: " + opcode[1] + " input: " + input[opcode[2]] + " output: " + output[opcode[3]]);
        if (input[opcode[1]]*input[opcode[2]] == output[opcode[3]]) {
            return true;
        } else {
            return false;
        }
    }

    public static boolean muli(int[] input, int[] output, int[] opcode) {
        if (input[opcode[1]]*opcode[2] == output[opcode[3]]) {
            return true;
        } else {
            return false;
        }
    }

    /*
    Bitwise AND:
banr (bitwise AND register) stores into register C the result of the bitwise AND of register A and register B.
bani (bitwise AND immediate) stores into register C the result of the bitwise AND of register A and value B.
*/
    public static boolean banr(int[] input, int[] output, int[] opcode) {
        int c = input[opcode[1]] & input[opcode[2]];
        if (c == output[opcode[3]]) {
            return true;
        } else {
            return false;
        }
    }

    public static boolean bani(int[] input, int[] output, int[] opcode) {
        int c = input[opcode[1]] & opcode[2];
        if (c == output[opcode[3]]) {
            return true;
        } else {
            return false;
        }
    }

    /*
Bitwise OR:
borr (bitwise OR register) stores into register C the result of the bitwise OR of register A and register B.
bori (bitwise OR immediate) stores into register C the result of the bitwise OR of register A and value B.
*/
    public static boolean borr(int[] input, int[] output, int[] opcode) {
        int c = input[opcode[1]] | input[opcode[2]];
        if (c == output[opcode[3]]) {
            return true;
        } else {
            return false;
        }
    }

    public static boolean bori(int[] input, int[] output, int[] opcode) {
        int c = input[opcode[1]] | opcode[2];
        if (c == output[opcode[3]]) {
            return true;
        } else {
            return false;
        }
    }

    /*
Assignment:
setr (set register) copies the contents of register A into register C. (Input B is ignored.)
seti (set immediate) stores value A into register C. (Input B is ignored.)
*/
    public static boolean setr(int[] input, int[] output, int[] opcode) {
        if (input[opcode[1]] == output[opcode[3]]) {
            return true;
        } else {
            return false;
        }
    }

    public static boolean seti(int[] input, int[] output, int[] opcode) {
        if (opcode[1] == output[opcode[3]]) {
            return true;
        } else {
            return false;
        }
    }

    /*
Greater-than testing:
gtir (greater-than immediate/register) sets register C to 1 if value A is greater than register B. Otherwise, register C is set to 0.
gtri (greater-than register/immediate) sets register C to 1 if register A is greater than value B. Otherwise, register C is set to 0.
gtrr (greater-than register/register) sets register C to 1 if register A is greater than register B. Otherwise, register C is set to 0.
*/
    public static boolean gtir(int[] input, int[] output, int[] opcode) {
        if ( ((opcode[1] > input[opcode[2]]) && output[opcode[3]] == 1 ) || ((opcode[1] <= input[opcode[2]]) && output[opcode[3]] == 0 )) {
            return true;
        } else {
            return false;
        }
    }

    public static boolean gtri(int[] input, int[] output, int[] opcode) {
        if ( ((input[opcode[1]] > opcode[2]) && output[opcode[3]] == 1 ) || ((input[opcode[1]] <= opcode[2]) && output[opcode[3]] == 0 )) {
            return true;
        } else {
            return false;
        }
    }

    public static boolean gtrr(int[] input, int[] output, int[] opcode) {
        if ( ((input[opcode[1]] > input[opcode[2]]) && output[opcode[3]] == 1 ) || ((input[opcode[1]] <= input[opcode[2]]) && output[opcode[3]] == 0 )) {
            return true;
        } else {
            return false;
        }
    }

/*
Equality testing:
eqir (equal immediate/register) sets register C to 1 if value A is equal to register B. Otherwise, register C is set to 0.
eqri (equal register/immediate) sets register C to 1 if register A is equal to value B. Otherwise, register C is set to 0.
eqrr (equal register/register) sets register C to 1 if register A is equal to register B. Otherwise, register C is set to 0.
     */

    public static boolean eqir(int[] input, int[] output, int[] opcode) {
        if ( ((opcode[1] == input[opcode[2]]) && output[opcode[3]] == 1 ) || ((opcode[1] != input[opcode[2]]) && output[opcode[3]] == 0 )) {
            return true;
        } else {
            return false;
        }
    }

    public static boolean eqri(int[] input, int[] output, int[] opcode) {
        if ( ((input[opcode[1]] == opcode[2]) && output[opcode[3]] == 1 ) || ((input[opcode[1]] != opcode[2]) && output[opcode[3]] == 0 )) {
            return true;
        } else {
            return false;
        }
    }

    public static boolean eqrr(int[] input, int[] output, int[] opcode) {
        if ( ((input[opcode[1]] == input[opcode[2]]) && output[opcode[3]] == 1 ) || ((input[opcode[1]] != input[opcode[2]]) && output[opcode[3]] == 0 )) {
            return true;
        } else {
            return false;
        }
    }

}