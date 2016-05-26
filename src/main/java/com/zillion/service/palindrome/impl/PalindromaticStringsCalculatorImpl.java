package com.zillion.service.palindrome.impl;

import com.zillion.service.palindrome.PalindromaticStringsCalculator;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Konstantin on 4/24/16.
 *
 * simpler solution that typically runs in linearthmic time:
 * First, describe how to find all palindromic substrings of length exactly L in linear time:
 *
 * use Karp-Rabin to iteratively form the hashes of each substring of length L
 * (and its reverse), and compare.
 * Since you don't know L, repeatedly double your guess of L until you know the optimal length
 * is between L and 2L. Then use binary search to find the exactly length.
 */
@Component
public class PalindromaticStringsCalculatorImpl implements PalindromaticStringsCalculator {

    @Override
    public int calculatePalindromes(String inventorName) {


        String name = inventorName;
        name = name.replaceAll("\\s+","");

        ArrayList<Character> inputList = new ArrayList<>();
        for (int i = 0; i < name.length(); i++) {
            inputList.add(name.charAt(i));
        }

        List<String> results = generatePalindromes(inputList, name.length());

        return results.size();
    }



    private List<String> generatePalindromes(ArrayList<Character> charSet, int size) {
        List<String> resultsList = new ArrayList<>();

        Boolean odd = false;
        if ( (size & 1) > 0 ) {/* odd */
            odd = true;
        }
        generatePalindromes("", charSet, size, odd, resultsList);

        return resultsList;
    }

    private void generatePalindromes(String string, ArrayList<Character> charSet, int size, Boolean odd, List<String> resultsList) {
        if (string == null) {
            return;
        }

        if (string.length() == size) {
            resultsList.add(string);

        }

        for (int i = 0; i < charSet.size(); i++) {

            if (odd && string.isEmpty()) {
                generatePalindromes(string + charSet.get(i), charSet, size, odd, resultsList);
            }

            else {
                generatePalindromes(charSet.get(i) + string + charSet.get(i), charSet, size, odd, resultsList);
            }

        }

    }


}
