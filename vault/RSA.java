package vault;
import java.security.SecureRandom;
import java.math.BigInteger;
import java.util.ArrayList;

public class RSA{
    BigInteger n;
    BigInteger e;
    BigInteger d;
    int maxLength;
RSA(){
    keyGenerator();
}
RSA(BigInteger n,BigInteger e,BigInteger d){
    this.n=n;
    this.e=e;
    this.d=d;
}
void keyGenerator(){
SecureRandom random = new SecureRandom();
BigInteger p = new BigInteger(2048,random).setBit(0); 
BigInteger q = new BigInteger(2048,random).setBit(0);
while(!p.isProbablePrime(10)){
    p=new BigInteger(2048,random).setBit(0);
}
while(!q.isProbablePrime(10) || p.equals(q)){
    q=new BigInteger(2048,random).setBit(0);
}
n=p.multiply(q);
BigInteger nOfPi=(p.subtract(BigInteger.valueOf(1))).multiply(q.subtract(BigInteger.valueOf(1)));
e=new BigInteger("65537");
 d=e.modInverse(nOfPi);

maxLength=(n.bitLength()-1)/8;
    }

BigInteger encrypt(String password){
    byte[] arr=password.getBytes();
    BigInteger msg=new BigInteger(1,arr);
return msg.modPow(e,n);
}
String decrypt(BigInteger encryptedpassword){
     return new String(encryptedpassword.modPow(d,n).toByteArray());
}
}

    //         byte[] messageBytes = message.getBytes();
    //     BigInteger encrypted=rsa.encrypt(messageBytes);
    //     BigInteger decrypted=rsa.decrypt(encrypted.toByteArray());
    //     System.out.println(new String(decrypted.toByteArray()));

