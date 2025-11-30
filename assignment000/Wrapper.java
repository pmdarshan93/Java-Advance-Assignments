public class Wrapper{
    public static void main(String[] args) {
        //---------  Static Methods -------------
        Integer a=new Integer("226");
        //  ( to count the no of 1 in the binary representation of the given number)- returns int - Parameter ( int a)
        System.out.println("Integer Static bit Count "+Integer.bitCount(12));
        // (To compare two int) -1 if first is small, 0 if both are equal, 1 if first value is big - returns int - paramenter ( int a, int b )
        System.out.println("Integer compare two ints : "+Integer.compare(2003,202));
        //  There is no signed bit and the last bit ( 0 or 1 which is responsible for sign )is also calculated - returns int - parameter ( int a, int b )
        System.out.println("Integer compare two ints unsigned : "+Integer.compareUnsigned(200,2001));
        // Converts a string into int  -  returns int - parameter ( String a) -- we can convert any input octa, hex, or anyother type ( it will auto matically detect it)
        System.out.println("Decode of a string : "+Integer.decode("234"));
        // Divides unsigned versions of a by b - return int - parameter( int a , int b)
        System.out.println("Unsigned division : "+Integer.divideUnsigned(-1,12));
        // Returns the reminder ( like modulo operator ) - return int
        System.out.println("Unsigned Reminder : "+Integer.remainderUnsigned(-1,12));
        // for Integer the number itself is the hash code - return int
        System.out.println("Hash Code :  "+Integer.hashCode(a));
        //  In the binary representation other than the msb(most siginificant bit ) changes all other into 0 and returns it. ex: 1010 ( which is 10 ) returns 1000 ( which is 8 highest one bit value) -- return int
        System.out.println("Hightest one Bit : "+Integer.highestOneBit(a));
        // Opposit of highest one bit ( focus the least significant bit ) return int
        System.out.println("Lowest One Bit : "+Integer.lowestOneBit(a));
        //
        System.out.println("Max value (226,232) : "+Integer.max(a,232)+"\tMin value (226,232) : "+Integer.min(a,232));
        // no of zeros before msb . ( seeing it in right to left) - return int
        System.out.println("Number of leading zeros : "+Integer.numberOfLeadingZeros(a));
        // to print the binary value -- return String
        System.out.println("Binary representation oa 226 : "+Integer.toBinaryString(a));
        // to print the octal value -- return String
        System.out.println("Octal representation oa 226 : "+Integer.toOctalString(a));
        // to print the hexadeciaml value -- return String
        System.out.println("hexa decimal representation oa 226 : "+Integer.toHexString(a));
        // no of zeros after lsb ( seeing it in right to left ) return int
        System.out.println("Number of trailing zeros : "+Integer.numberOfTrailingZeros(a));
        // It is not like the decode if we give different type of number like octal,hecta,or binary we want to mention it other wise it only parse the decimal number not other type -- return int
        System.out.println("Parse Int (String ) : "+Integer.parseInt("123")); 
        // here the radix is indicating the number system of given string, 2- binary, 8-octal,10-decimal(Default), 16 - hexadecimal
        System.out.println("Parse int ( string, radix ) : "+Integer.parseInt("100",2));
        //  it is same as unsigned method -- return int
        System.out.println("parseUnsignedInt(String s) : "+Integer.	parseUnsignedInt("12"));
        //
        System.out.println("parseUnsignedInt(String s, int radix) : "+Integer.parseUnsignedInt("FF", 16));
        // It reverse the bit values ( 1010 for 10 ------ > 0101 reversed) -- return int
        System.out.println("Reverse : "+Integer.reverse(a)+"  Binary representation of the reversed value : "+Integer.toBinaryString(Integer.reverse(a)));
        // It separte the int into 4 bytes then reverse the 4 bytes (binary representation 11 22 33 44 ----> 44 33 22 11 )
        System.out.println("Reverse Bytes : "+Integer.reverseBytes(a)+"  Binary representation of the byte reversed value : "+Integer.toBinaryString(Integer.reverseBytes(a)));
        // moving digits in left n times (10111,2)--->(11101)
        System.out.println("Rotate left : "+Integer.rotateLeft(12,5));
         // moving digits in right n times (10111,2)--->(11101)
        System.out.println("Rotate right : "+Integer.rotateLeft(12,5));
        // it is used to identity wthether the number is pos,neg or 0 returns 1,0,-1 (Like comparing the int value with 0)
        System.out.println("Signum : "+Integer.signum(a));
        // 
        System.out.println("To string : "+Integer.toString(a));
        //
        System.out.println("To string with radix : "+Integer.toString(a,8));
        //
        System.out.println("To string : "+Integer.toString(a));
        // Value of will return the Integer class object
        System.out.println("Value of : "+Integer.valueOf(234));
        //---------  Instance Methods -------------
         // Instance method () will return the byte value ( after 128 it will start from -128)
        System.out.println(a.byteValue());

    }
}

///////////////
// Get Integer - Java system property
