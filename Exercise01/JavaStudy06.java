import java.util.*;

// 백엔드 19기 임국희
public class JavaStudy06 {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        VoteManager manager = new VoteManager(scanner);
        manager.askForTotalOfVotes();
        manager.askForSize();
        manager.askForNamesOfcandidates();

        scanner.close();
        manager.startVote();
    }
}

class VoteManager{
    private Scanner scanner = null;
    private int totalVoteCount, candidateCount, votedCount = 0;
    private HashMap<String, Integer> candidates = null;
    private String electee;

    VoteManager(Scanner scanner) {
        this.scanner = scanner;
    }

    private int askForNumericInput(String prompt) {
        System.out.print(prompt);
        return Integer.parseInt(scanner.nextLine());
    }
    void askForTotalOfVotes() {
        totalVoteCount = askForNumericInput("총 진행할 투표수를 입력해 주세요.");
    }
    void askForSize() {
        candidateCount = askForNumericInput("가상 선거를 진행할 후보자 인원을 입력해 주세요.");

    }
    void askForNamesOfcandidates() {
        candidates = new HashMap<>();
        for (int i = 0; i < candidateCount; i++) {
            System.out.printf("%d번째 후보자이름을 입력해 주세요.",i+1);
            candidates.put(scanner.nextLine().trim(),0);
        }
    }
    private int getVotedCount() {
        return votedCount;
    }
    private int getVotedCount(String name) {
        return candidates.get(name);
    }
    private double getVoteShare(String name) {
        return getVotedCount(name)*100.0/ totalVoteCount;
    }
    private double getProgressRatio() {
        return getVotedCount()*100.0/ totalVoteCount;
    }
    String vote(ArrayList<String> keySet, int n) {
        String name = keySet.get(n);
        candidates.put(name,candidates.get(name)+1);
        votedCount++;
        return name;
    }
    void printVote(ArrayList<String> keySet) {
        Random random = new Random();
        String name = vote(keySet,random.nextInt(candidateCount));
        System.out.printf("[투표진행율]:%5.2f, %5d명 투표 ==> %10s\n",getProgressRatio(),getVotedCount(),name);
        for (int i = 0; i < candidateCount; i++) {
            name = keySet.get(i);
            System.out.printf("[기호:%d] %-10s: %5.2f (투표수: %5d)\n",i+1, name,getVoteShare(name),getVotedCount(name));
        }
        System.out.println();
    }
    void printElectee() {
        System.out.println("[투표결과] 당선인 : " + electee);
    }
    void startVote() {
        ArrayList<String> keySet = new ArrayList<>(candidates.keySet());
        for (int i = 0; i < totalVoteCount; i++) {
            printVote(keySet);
        }
        int voted = -1;
        for (String name: keySet) {
            if(voted < candidates.get(name)) {
                voted = candidates.get(name);
                electee = name;
            }
        }
        printElectee();
    }
}
