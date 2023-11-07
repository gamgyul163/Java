import java.util.*;

// 백엔드 19기 임국희
public class JavaStudy06 {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        VoteManager manager = new VoteManager(scanner);
        manager.askForTotalOfVotes();
        manager.askForNumberOfCandidates();
        manager.askForNamesOfCandidates();

        for (int i = 1; i <= manager.getTotalOfVotes() ; i++) {
            manager.printVote(i, manager.doVote());
        }

        manager.printElectee();
        scanner.close();
    }
}

// 후보자 클래스, 이름과 기호를 저장
class Candidate {
    private final String name;
    private final int candidateNumber; // 기호
    private int numberOfVotes = 0; // 득표수

    Candidate (String name, int candidateNumber) {
        this.name = name;
        this.candidateNumber = candidateNumber;
    }
    public String getName() {
        return name;
    }
    public void addNumberOfVotes(int votes) { // 표를 추가하고 추가된 후의 득표수를 출력한다.
        numberOfVotes += votes;
    }
    public int getNumberOfVotes() {
        return numberOfVotes;
    }
}

// 선거를 진행하는 클래스
class VoteManager{
    private final Scanner scanner;
    private int totalOfVotes = 0; // 총 투표수
    Candidate[] candidates = null;
    final String INPUT_ERROR_MESSAGE = "잘못된 입력입니다. 다시 입력해주세요";

    VoteManager(Scanner scanner) {
        this.scanner = scanner;
    }
    int getTotalOfVotes() {
        return totalOfVotes;
    }
    // 숫자 입력을 요청하고 입력값 유효성을 체크하는 메소드
    private int askForNumericInput(String prompt, int minLength, int maxLength, int min, int max) { // 입력 요청 문구, 최소 전체 자릿수, 최대 전체 자릿수 최소값, 최대값
        int numericInput = min-1;
        while (numericInput < min || numericInput > max) {
            System.out.print(prompt);
            String strInput = scanner.nextLine().trim();
            if(strInput.matches("\\d{" + minLength + "," + maxLength + "}")) { // 정규 표현식으로 자릿수 검사
                numericInput = Integer.parseInt(strInput);
            }
            if (numericInput < min || numericInput > max) { // 자릿수 안맞는 경우도 min-1이기 때문에 if에 걸리게 된다.
                System.out.println(INPUT_ERROR_MESSAGE);
            }
        }
        return numericInput;
    }
    void askForTotalOfVotes() {
        totalOfVotes = askForNumericInput("총 진행할 투표수를 입력해 주세요.", 1, 5, 1, 10000);
    }
    void askForNumberOfCandidates() { // 후보자 수를 입력 받아서 후보자 배열을 생성한다.
        int numberOfCandidates = askForNumericInput("가상 선거를 진행할 후보자 인원을 입력해 주세요.", 1, 2, 1, 10);
        candidates = new Candidate[numberOfCandidates];
    }
    void askForNamesOfCandidates() { // 후보자 이름을 입력받아서 배열에 저장한다. 인원수가 입력되지 않은 상태면 인원수를 입력하게 한다. 이름이 유효하지 않으면 재입력하게 한다.
        if (candidates == null) {
            askForNumberOfCandidates();
        }
        for (int i = 0; i < candidates.length; i++) {
            while (candidates[i] == null) {
                System.out.printf("%d번째 후보자이름을 입력해 주세요.", i+1);
                String strInput = scanner.nextLine();
                if (strInput.matches("[가-힣]{1,9}")){ // 정규 표현식으로 검사
                    candidates[i] = new Candidate(strInput, i+1);
                } else {
                    System.out.println(INPUT_ERROR_MESSAGE);
                }
            }
        }
        System.out.println();
    }
    // 후보자의 기호를 입력받아 해당 후보자의 득표수를 가져온다.
    private int getVotedCount(int candidateNumber) {
        return candidates[candidateNumber-1].getNumberOfVotes();
    }
    // 후보자의 기호를 입력받아 해당 후보자의 득표율을 가져온다.
    private double getVoteShare(int candidateNumber) {
        return getVotedCount(candidateNumber)*100./totalOfVotes;
    }
    // 후보자중 1명에게 랜덤하게 1표를 투표하고 후보자 기호를 리턴한다.
    int doVote() {
        Random random = new Random();
        int candidateNumber = random.nextInt(candidates.length) + 1;
        candidates[candidateNumber - 1].addNumberOfVotes(1);
        return candidateNumber;
    }
    // 현재 표 수와 득표자 기호를 입력받아 선거 화면을 다시 그린다.
    void printVote(int curVotes, int candidateNumber) {
        System.out.printf("[투표진행율]: %.2f%%, %d명 투표 ==> %s\n",curVotes*100./totalOfVotes, curVotes, candidates[candidateNumber-1].getName());
        for (int i = 0; i < candidates.length; i++) {
            int nameStrLength = 10 - candidates[i].getName().length();
            System.out.printf("[기호:%d] %-"+ nameStrLength +"s %5.2f%% (투표수: %d)\n",i+1, candidates[i].getName()+":",getVoteShare(i+1),getVotedCount(i+1));
        }
        System.out.println();
    }
     private String getElectee() { // 동률 시 처리에 대한 지시가 별도로 없으므로 그냥 빠르게 반환되는 max값을 당선된 걸로 친다..
        int maxVotes = 0;
        String elctee = "";
        for (Candidate candidate:candidates) {
            if (candidate.getNumberOfVotes() > maxVotes) {
                maxVotes = candidate.getNumberOfVotes();
                elctee = candidate.getName();
            }
        }
        return elctee;
    }
    void printElectee() {
        System.out.println("[투표결과] 당선인 : " + getElectee());
    }
}
