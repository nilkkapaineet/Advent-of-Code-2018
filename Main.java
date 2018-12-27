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

        String[] orders = new String[16];

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
            // there are 16 opcodes...
            int[][] opcodeNumbers = new int[16][16];
            for (int index1 = 0; index1 < 16; index1++) {
                for (int index2 = 0; index2 < 16; index2++) {
                    opcodeNumbers[index1][index2] = 0;
                }
            }

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
                    if (lineNumber % 4 == 0) {
                        input[i] = Integer.parseInt(m.group());
                    }
                    if (lineNumber % 4 == 1) {
                        opcode[i] = Integer.parseInt(m.group());
                    }
                    if (lineNumber % 4 == 2) {
                        output[i] = Integer.parseInt(m.group());
                    }
                    i++;
                }
                int opcodes = 0;
                // opcode[0] is an opcode number which needs to be figured out.. which number belongs to certain opcode
                if (lineNumber % 4 == 2) {
                    // go through opcodes
                    if (addr(input, output, opcode)) {
                        opcodes++;
                        opcodeNumbers[0][opcode[0]]++;
                    }
                    if (addi(input, output, opcode)) {
                        opcodes++;
                        opcodeNumbers[1][opcode[0]]++;
                    }
                    if (muli(input, output, opcode)) {
                        opcodes++;
                        opcodeNumbers[2][opcode[0]]++;
                    }
                    if (mulr(input, output, opcode)) {
                        opcodes++;
                        opcodeNumbers[3][opcode[0]]++;
                    }
                    if (bori(input, output, opcode)) {
                        opcodes++;
                        opcodeNumbers[4][opcode[0]]++;
                    }
                    if (borr(input, output, opcode)) {
                        opcodes++;
                        opcodeNumbers[5][opcode[0]]++;
                    }
                    if (bani(input, output, opcode)) {
                        opcodes++;
                        opcodeNumbers[6][opcode[0]]++;
                        // opcodeNumber 6, opcode[0]=10, 54 pts, bani
                    }
                    if (banr(input, output, opcode)) {
                        opcodes++;
                        opcodeNumbers[7][opcode[0]]++;
                    }
                    if (seti(input, output, opcode)) {
                        opcodes++;
                        opcodeNumbers[8][opcode[0]]++;
                    }
                    if (setr(input, output, opcode)) {
                        opcodes++;
                        opcodeNumbers[9][opcode[0]]++;
                    }
                    if (gtir(input, output, opcode)) {
                        opcodes++;
                        opcodeNumbers[10][opcode[0]]++;
                    }
                    if (gtri(input, output, opcode)) {
                        opcodes++;
                        opcodeNumbers[11][opcode[0]]++;
                    }
                    if (gtrr(input, output, opcode)) {
                        opcodes++;
                        opcodeNumbers[12][opcode[0]]++;
                    }
                    if (eqir(input, output, opcode)) {
                        opcodes++;
                        opcodeNumbers[13][opcode[0]]++;
                    }
                    if (eqri(input, output, opcode)) {
                        opcodes++;
                        opcodeNumbers[14][opcode[0]]++;
                    }
                    if (eqrr(input, output, opcode)) {
                        opcodes++;
                        opcodeNumbers[15][opcode[0]]++;
                    }
                }
                if (opcodes >= 3) {
                    totalOpcodes++;
                }
                lineNumber++;
            }
            // Always close files.
            bufferedReader.close();


            System.out.println("Total opcodes: " + totalOpcodes);


            String[] ordersInit = {"addr", "addi", "muli", "mulr",
                    "bori", "borr", "bani", "banr",
                    "seti", "setr", "gtir", "gtri",
                    "gtrr", "eqir", "eqri", "eqrr"};

/*
            // figure out opcode nnumbers corresponding to certain opcode
            for(int index1=0; index1<16; index1++) {
               System.out.println("opcode number " + index1 + ": ");
                for (int index2=0; index2<16; index2++) {
                    System.out.print(opcodeNumbers[index1][index2] + " ");
                    // opcodeNumber 6, opcode[0]=10, 54 pts, bani
                }
                System.out.println("");
            }
*/
            // diminish options...
            for (int index1 = 0; index1 < 16; index1++) {
                int howManyZeros = 0;
                int notZero = 0;
//                System.out.println("index1: " + index1);
                for (int index2 = 0; index2 < 16; index2++) {
                    if (opcodeNumbers[index1][index2] == 0) {
                        howManyZeros++;
                    } else {
                        // opcodeNumber 6, opcode[0]=10, 54 pts, bani
                        notZero = index2;
                    }
                }
                if (howManyZeros == 15) {
                    // notZero is only not a zero, therefore, the opcode number is found
                    // make every other column zero as well
                    // System.out.println("not zero: " + notZero + " index1: " + index1);
                    opcodeNumbers = makeThemZero(opcodeNumbers, notZero);
                    // opcodeNumber 6, opcode[0]=10, 54 pts, bani
                    orders[notZero] = ordersInit[index1];
                    // start from the beginning
                    index1 = -1;
                }
            }
            
/*
            // print again
            System.out.println("-------------------------print again -----------------------------------------");
            for(int index1=0; index1<16; index1++) {
                System.out.println("opcode number " + index1 + ": ");
                for (int index2=0; index2<16; index2++) {
                    System.out.print(opcodeNumbers[index1][index2] + " ");
                }
                System.out.println("");
            }
*/
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

        //---------------------------------------------------------------------------------------------------------------------
        // The name of the file to open.
        fileName = "file1.txt";

        // This will reference one line at a time
        line = "";

        int[] input = {0, 0, 0, 0};
        try {
            // FileReader reads text files in the default encoding.
            FileReader fr =
                    new FileReader(fileName);

            // Always wrap FileReader in BufferedReader.
            BufferedReader br =
                    new BufferedReader(fr);

            int lineNumber = 0;
            int[] opcode = new int[4];

            while ((line = br.readLine()) != null) {
                Pattern p = Pattern.compile("-?[0-9]+");
                Matcher m = p.matcher(line);
                int i = 0;
                while (m.find()) {
                    opcode[i] = Integer.parseInt(m.group());
                    i++;
                }

                String order = orders[opcode[0]];
                // go through opcodes
                if (order.equals("addr")) {
                    input = addr(input, opcode);
                }
                if (order.equals("addi")) {
                    input = addi(input, opcode);
                }
                if (order.equals("muli")) {
                    input = muli(input, opcode);
                }
                if (order.equals("mulr")) {
                    input = mulr(input, opcode);
                }
                if (order.equals("bori")) {
                    input = bori(input, opcode);
                }
                if (order.equals("borr")) {
                    input = borr(input, opcode);
                }
                if (order.equals("bani")) {
                    input = bani(input, opcode);
                }
                if (order.equals("banr")) {
                    input = banr(input, opcode);
                }
                if (order.equals("seti")) {
                    input = seti(input, opcode);
                }
                if (order.equals("setr")) {
                    input = setr(input, opcode);
                }
                if (order.equals("gtir")) {
                    input = gtir(input, opcode);
                }
                if (order.equals("gtri")) {
                    input = gtri(input, opcode);
                }
                if (order.equals("gtrr")) {
                    input = gtrr(input, opcode);
                }
                if (order.equals("eqir")) {
                    input = eqir(input, opcode);
                }
                if (order.equals("eqri")) {
                    input = eqri(input, opcode);
                }
                if (order.equals("eqrr")) {
                    input = eqrr(input, opcode);
                }
            }
            // Always close files.
            br.close();

            System.out.println("register 0 : " + input[0]);


        } catch (FileNotFoundException ex) {
            System.out.println(
                    "Unable to open file '" +
                            fileName + "'");
        } catch (IOException ex)

        {
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
    public static int[][] makeThemZero(int[][] opcodes, int notZero) {
        for(int index1=0; index1<16; index1++) {
    //        System.out.println("make them zero: " + index1 + " and " + notZero);
            opcodes[index1][notZero] = 0;
        }
        return opcodes;
    }

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
    // register0,1,2,3
    // addi 0 1 2 ==> 0 0 1 2
    //
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

    public static int[] mulr(int[] input, int[] opcode) {
        //       System.out.println("opcode: " + opcode[1] + " input: " + input[opcode[2]] + " output: " + output[opcode[3]]);
        input[opcode[3]] = input[opcode[1]]*input[opcode[2]];
        return input;
    }

    public static int[] muli(int[] input, int[] opcode) {
        input[opcode[3]] = input[opcode[1]]*opcode[2];
        return input;
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

    public static int[] banr(int[] input, int[] opcode) {
        input[opcode[3]] = input[opcode[1]] & input[opcode[2]];
        return input;
    }

    public static int[] bani(int[] input, int[] opcode) {
        input[opcode[3]] = input[opcode[1]] & opcode[2];
        return input;
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

    public static int[] borr(int[] input, int[] opcode) {
        input[opcode[3]] = input[opcode[1]] | input[opcode[2]];
        return input;
    }

    public static int[] bori(int[] input, int[] opcode) {
        input[opcode[3]] = input[opcode[1]] | opcode[2];
        return input;
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

    public static int[] setr(int[] input, int[] opcode) {
        input[opcode[3]] = input[opcode[1]];
        return input;
    }

    public static int[] seti(int[] input, int[] opcode) {
        input[opcode[3]] = opcode[1];
        return input;
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

    public static int[] gtir(int[] input, int[] opcode) {
        if (opcode[1] > input[opcode[2]]) {
            input[opcode[3]] = 1;
        } else {
            input[opcode[3]] = 0;
        }
        return input;
    }

    public static int[] gtri(int[] input, int[] opcode) {
        if (input[opcode[1]] > opcode[2]) {
            input[opcode[3]] = 1;
        } else {
            input[opcode[3]] = 0;
        }
        return input;
    }

    public static int[] gtrr(int[] input, int[] opcode) {
        if (input[opcode[1]] > input[opcode[2]]) {
            input[opcode[3]] = 1;
        } else {
            input[opcode[3]] = 0;
        }
        return input;
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

    public static int[] eqir(int[] input, int[] opcode) {
        if (opcode[1] == input[opcode[2]]) {
            input[opcode[3]] = 1;
        } else {
            input[opcode[3]] = 0;
        }
        return input;
    }

    public static int[] eqri(int[] input, int[] opcode) {
        if (input[opcode[1]] == opcode[2]) {
            input[opcode[3]] = 1;
        } else {
            input[opcode[3]] = 0;
        }
        return input;
    }

    public static int[] eqrr(int[] input, int[] opcode) {
        if (input[opcode[1]] == input[opcode[2]]) {
            input[opcode[3]] = 1;
        } else {
            input[opcode[3]] = 0;
        }
        return input;
    }
}