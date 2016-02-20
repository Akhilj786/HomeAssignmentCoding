import java.io.*;
import java.util.*;
import java.text.*;
import java.math.*;

public class FriendCircle{
    static void findFriends(int friendNum, String[] friends, boolean[] visited) {
        for (int i = 0; i < friends.length; i++) {
            if (!visited[i] && 'Y' == friends[friendNum].charAt(i)) {
                visited[i] = true;
                findFriends(i, friends, visited);
            }
        }
    }

    static int friendCircles(String[] friends) {
        // Empty or null friends.
        if (null == friends || friends.length < 1) {
            return 0;
        }

        boolean visited[] = new boolean[friends.length]; // Keeps a track of visited friends.
        int count = 0; 
        for (int friendNum = 0; friendNum < friends.length; friendNum++) {
            if (!visited[friendNum]) {
                count++;
                visited[friendNum] = true;
                findFriends(friendNum, friends, visited);
            }
        }

        return count;

    }


    public static void main(String args[]){
        String[] friends = {"YYNN",
                            "YYYN",
                            "NYYN",
                            "NNNY"};
        
        int res = friendCircles(friends);
        System.out.println(res);
    }

}

