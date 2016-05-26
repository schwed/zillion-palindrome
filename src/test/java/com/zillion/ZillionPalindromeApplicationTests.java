package com.zillion;

import org.hibernate.validator.constraints.Length;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import java.math.BigInteger;
import java.util.*;

//@RunWith(SpringJUnit4ClassRunner.class)
//@SpringApplicationConfiguration(classes = ZillionPalindromeApplication.class)
//@WebAppConfiguration
public class ZillionPalindromeApplicationTests {

    List<String> results;
    char[] chars;

    int lengthMinusOne, halfLength;

    @Ignore
    @Test
    public void contextLoads() {
    }

    @Test
    public void palindromes() throws Exception {
        String inventorName = "NicolaTesla".toLowerCase();
        solve(inventorName);
        solveUsa(inventorName);

        /*
        String input = "neveroddoreven";


        char[] inpChars = new char[inventorName.length()];
        input.getChars(0, inventorName.length(), inpChars, 0);

        System.out.println(countPalinAnags(inpChars));
        */

        //konstantin("adisabeba");

        // it says it can't make palidrome from investorName
        //generatePalindromesBest(inventorName);
        //System.out.println(results.size());

        // this one prints just a letter 'a'
        //russianPalindrome(inventorName);


        //generateAllPalindromes(inventorName);
        /*
        List<String> generatedPalindromesList = generatePalindromes(inventorName);
        System.out.println("generated size: " + generatedPalindromesList.size());
        for (String s : generatedPalindromesList) {
            System.out.println(s);
        }
        */
        //manacher(inventorName);
        //karpPalindromes(inventorName);

        /*
        List<String> palindromesList = generatePalindromes(inventorName);

        System.out.println("done generating: " + palindromesList.size());
        for (String s : palindromesList) {
            System.out.println(s);
        }
        */
        // permutations
        /*
        char input[] = inventorName.toCharArray();
        char permute[] = new char[input.length];
        permutations(input, permute, input.length, 0);
        */

        /*
        List<String> pList = shortest(inventorName);
        for (String s : pList) {
            System.out.println(s);
        }
        System.out.println("shortest done");

        List<String> list = longest(inventorName);
        for (String s: list) {
            System.out.println(s);
        }
        System.out.println("longest done");
        */
        // loops forever and i had to stop the debugger
        //List<String> stringList = palindromesPermutation(inventorName);
        //System.out.println(stringList.size());
        /*
        for (String s : printAll(inventorName))
            System.out.println(s);
        System.out.println("printall done");
        */


        // this one generates much more then needed
        /*
        ArrayList<Character> inputList = new ArrayList<>();
        for (int i = 0; i < inventorName.length(); i++) {
            inputList.add(inventorName.charAt(i));
        }

        results = new ArrayList<>();
        sizeMPalindromes(inputList, inventorName.length());

        System.out.println("size m palindromes: " + results.size());
        */
        /*
        for (String str : results) {

                System.out.println(str);

        }
        */


        //shortestPalindrome(inventorName);
        //System.out.println("shortest palindrome done.");


        // does not work as i ported it from c++
        // /printAllPalindromes("abracarabrsabba");

        // nothing is output from inventor name
        //allPalindromesFromString(inventorName);
        /*
        Set<String> stringSet = allPalindromes(inventorName);
        Iterator<String> stringIterator = stringSet.iterator();
        while(stringIterator.hasNext()) {
            System.out.println(stringIterator.next());
        }
        */
        /*
        subPal(inventorName);

        palindrome(inventorName);
        */
        System.out.println("print all palindromes done");
    }

    //////////////////////////////////////////////////////////////////////////////////////////
    private void solve(String inventorName) {
        long MOD = 1000000007;
        int n = inventorName.length();
        long[] facs = generateFactorial(100005, MOD);
        long[] inv = generateReverseFactorials(100005, MOD);
        long ans = facs[n / 2];
        int[] cnt = new int[26];
        for (int i = 0; i < n; i++) {
            cnt[inventorName.charAt(i) - 'a']++;
        }


        for (int i = 0; i < 26; i++) {
            ans = (ans * inv[cnt[i]/2]);
            if(ans >= MOD) ans %= MOD;
        }
        System.out.println(ans);
    }

    private long[] generateFactorial(int count, long module) {
        long[] result = new long[count];
        if (module == -1) {
            if (count != 0)
                result[0] = 1;
            for (int i = 1; i < count; i++)
                result[i] = result[i - 1] * i;
        } else {
            if (count != 0)
                result[0] = 1 % module;
            for (int i = 1; i < count; i++)
                result[i] = (result[i - 1] * i) % module;
        }
        return result;
    }

    private long[] generateReverse(int upTo, long module) {
        long[] result = new long[upTo];
        if (upTo > 1)
            result[1] = 1;
        for (int i = 2; i < upTo; i++)
            result[i] = (module - module / i * result[((int) (module % i))] % module) % module;
        return result;
    }

    private long[] generateReverseFactorials(int upTo, long module) {
        long[] result = generateReverse(upTo, module);
        if (upTo > 0)
            result[0] = 1;
        for (int i = 1; i < upTo; i++)
            result[i] = result[i] * result[i - 1] % module;
        return result;
    }


    ///////////////// USA /////////////////////////
    private void solveUsa(String s){
        long mod = 1000000007;
        int[] counts = new int[26];
        for (char c : s.toCharArray()) {
            counts[c - 'a']++;
        }
        int odd = 0;
        long ans = 1;
        int total = 0;
        for (int count : counts) {
            if (count % 2 == 1) {
                odd++;
            }
            ans = (ans * c(total + count / 2, count / 2, mod)) % mod;
            total += count / 2;
        }
        System.out.println(odd <= 1 ? ans : ans);
    }

    private long c(int n, int k, long mod ) {
        long nom = 1, denom = 1;
        for (int i = 0; i < k; ++i) {
            nom = (nom * (n - i)) % mod;
            denom = (denom * (i + 1)) % mod;
        }
        return (nom * BigInteger.valueOf(denom).modInverse(BigInteger.valueOf(mod)).longValue()) % mod;
    }


}
