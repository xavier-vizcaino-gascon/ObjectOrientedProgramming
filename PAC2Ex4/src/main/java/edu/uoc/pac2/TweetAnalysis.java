package edu.uoc.pac2;

/**
 * This class integrates several methods for tweet processing
 * @author Xavier Vizcaino
 * @version 1.0
 */

public class TweetAnalysis {
    /**
     * Method for getting the number of hashtags [or the number of char #] associated to a given tweet
     * @param tweetText provided as String
     * @return number of '#' found in tweetText
     */
    public int countHashtags(String tweetText) {

        //Convert String to Char Array & initialize counter
        char[] tweetArray = tweetText.toCharArray();
        int hashtags = 0;

        //Loop Array
        for (char letter:tweetArray){
            if (letter=='#'){
                hashtags++; //Increase counter when conditions are met
            }
        }
        return hashtags;
    }

    /**
     * Method for adding '@' before username
     * @param tweetText text provided as string
     * @param usernames array with usernames
     * @return tweetText modified after inserting '@' before each username detected in tweetText
     */
    public String highlightUsernames(String tweetText, String[] usernames) {

        StringBuilder tweet = new StringBuilder(tweetText);

        //Loop through usernames
        for (String user : usernames) {

            //Get index of matching
            int index = tweet.indexOf(user);
            while (index!=-1){ //while user is found in remaining text
                tweet.insert(index, '@'); //insert @ in index position
                index=tweet.indexOf(user, index+2); //update index of next match
            }
        }
        return tweet.toString();
    }

    /**
     * Method for generating the tweet history
     * @param tweetTexts provided as String array
     * @return String with the following format: User @{USER_x} tweeted: '{text_y}
     *
     * It is considered the first occurrence of ':' in each string is the separator between username and tweeted text
     */
    public String getTweetHistory(String[] tweetTexts) {

        StringBuffer message = new StringBuffer();
        int i = 0;

        //Loop through tweet array
        for (String tweet: tweetTexts){

            StringBuffer pair = new StringBuffer(tweet);

            //tweet - message separator position
            int sep = pair.indexOf(":");

            //Get substrings
            String user = pair.substring(0,sep);
            String msg = pair.substring(sep+1,pair.length());

            //Format conditions to always add lineSeparator except for the last element
            if (i<tweetTexts.length-1){
                message.append("User @").append(user).append(" tweeted: ").append("'").
                        append(msg).append("'").append(System.lineSeparator());
            } else {
                message.append("User @").append(user).append(" tweeted: ").append("'").
                        append(msg).append("'");
            }
            i++;
        }
        return message.toString();
    }

    /**
     * Method to replicate input words until maxLength is reached
     * @param words provided as String array
     * @param maxLength maximum number of char allowed at the return
     * @return a string built with words provided in String array. String array must be repeated (reinitialized) when maxLength is not reached
     */
    public String botTweet(String[] words, int maxLength) {

        StringBuilder output= new StringBuilder();

        //Array of 0 length
        if (words.length==0){
            return "";
        }

        //Initiate index i & newWord
        int i=0;
        String newWord=words[i];

        //Loop
        while (output.length()+newWord.length()<maxLength){

            if (output.length()==0){ //First append to output
                output.append(newWord);
            } else { //Others append when output is not empty
                output.append(" ").append(newWord);
            }

            i++;
            if (i > words.length-1) {
                i = 0; //Reset words index
            }
            newWord=words[i]; //Update new word
        }
        return output.toString();
    }
}