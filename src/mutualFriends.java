import java.util.*;

public class mutualFriends {
    //Map to represent the friends network (Adjacency List)
    private Map<String, Set<String>> friendsNetwork;

    public mutualFriends() {
        friendsNetwork = new HashMap<>();
    }

    //Add a friendship between two users
    public void addFriendship(String usr1, String ussr2){
        friendsNetwork.computeIfAbsent(usr1, k -> new HashSet<>()).add(ussr2);
    }

    //check if two users are bidirectional friends
    public boolean isBiDirectionalFriend(String usr1, String usr2){
        return friendsNetwork.containsKey(usr1) && friendsNetwork.get(usr1).contains(usr2) &&
                friendsNetwork.containsKey(usr2) && friendsNetwork.get(usr2).contains(usr1);
    }

    public List<String> getSuggestions(String user){
        if(!friendsNetwork.containsKey(user)){
            return new ArrayList<>(); //User not found in the network
        }

        //Get the friends of the target user
        Set<String> usrfriends = friendsNetwork.get(user);
        for(String friend : friendsNetwork.get(user)){
            if(isBiDirectionalFriend(user, friend)){
                usrfriends.add(friend);
            }
        }

        //mutual friends for all other users
        Map<String, Integer> mutualFriendsCount = new HashMap<>();

        for(String friend : usrfriends){
            for(String potentialFriend : friendsNetwork.get(friend)){
                // only consider users who are not already bidirectional friends with the target user
                if (!potentialFriend.equals(user) && !usrfriends.contains(potentialFriend)){
                    mutualFriendsCount.put(potentialFriend, mutualFriendsCount.getOrDefault(potentialFriend, 0) + 1);
                }
            }
        }

        //Sort the potential friends by the number of mutual friends
        List<Map.Entry<String, Integer>> sortedSuggestions = new ArrayList<>(mutualFriendsCount.entrySet());
        sortedSuggestions.sort((e1, e2) -> e2.getValue().compareTo(e1.getValue()));

        //Extract the usernames from the sorted list
        List<String> suggestions = new ArrayList<>();
        for(Map.Entry<String, Integer> entry : sortedSuggestions){
            suggestions.add(entry.getKey());
        }
        return suggestions;
    }


    public static void main(String[] args) {
        Scanner sc = new  Scanner(System.in);
        mutualFriends fs = new mutualFriends();

        System.out.println("Enter the number of Users in the network ");
        int n = sc.nextInt();

        String[] usrs = new String[n];
        for(int i = 0; i < n; i++){
            System.out.println("Enter the name of the user: ");
            usrs[i] = sc.next();
        }
        System.out.println("Enter choice: ");
        System.out.println("1. Add a friendship");
        System.out.println("2. Get mutual friends");
        System.out.println("3. Exit");
        int opt = sc.nextInt();
        switch (opt){
            case 1:
                System.out.println("Enter the names of the users to be friends: ");
                String usr1 = sc.next();
                String usr2 = sc.next();
                fs.addFriendship(usr1, usr2);
                break;
            case 2:
                System.out.println("Enter the name of the user to get mutual friends: ");
                String usr = sc.next();
                List<String> suggestions = fs.getSuggestions(usr);
                System.out.println("The mutual friends are: ");
                for (String suggestion : suggestions) {
                    System.out.println(suggestion);
                }
                break;
            case 3:
                System.exit(0);
            default:
                System.out.println("Invalid choice");
        }
    }
}








