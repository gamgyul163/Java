package PreMission;// 백엔드 19기 임국희
import java.util.*;

public class JavaStudy06 {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        VoteManager voteManager = new VoteManager(scanner);
        voteManager.setVoteOptions();

        voteManager.printAllVote();
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
    public int getCandidateNumber() {
        return candidateNumber;
    }
}

// 선거 진행을 제어하는 클래스
class VoteManager{
    private final Scanner scanner;
    private int totalOfVotes; // 총 투표수
    private Candidate[] candidates; // 후보자들의 정보
    private final String INPUT_ERROR_MESSAGE = "잘못된 입력입니다. 다시 입력해주세요";
    private int numberOfVotes = 0;
    private static final int MAX_VOTES_LENGTH = 5;
    private static final int MAX_CANDIDATES = 10;
    VoteManager(Scanner scanner) {
        this.scanner = scanner;
    }
    // 숫자 입력을 요청하고 입력값 유효성을 체크하는 메소드
    private int askNumericInput(String prompt, int minLength, int maxLength, int min, int max) { // 입력 요청 문구, 최소 전체 자릿수, 최대 전체 자릿수 최소값, 최대값
        int numericInput = min-1;
        do {
            System.out.print(prompt);
            String strInput = scanner.nextLine().trim();
            if(strInput.matches("\\d{" + minLength + "," + maxLength + "}")) {
                numericInput = Integer.parseInt(strInput);
                if (numericInput >= min && numericInput <= max) {
                    break;
                }
            }
            System.out.println(INPUT_ERROR_MESSAGE);
        } while (true);
        return numericInput;
    }
    void setVoteOptions() { // 선거의 기본적인 값들을 설정하는 메소드
        askTotalOfVotes();
        askNumberOfCandidates();
        askNamesOfCandidates();
        System.out.println();
    }
    private void askTotalOfVotes() {
        totalOfVotes = askNumericInput("총 진행할 투표수를 입력해 주세요.", 1, 5, 1, 10000);
    }
    private void askNumberOfCandidates() { // 후보자 수를 입력 받아서 후보자 배열을 생성한다.
        int numberOfCandidates = askNumericInput("가상 선거를 진행할 후보자 인원을 입력해 주세요.", 1, 2, 1, 10);
        candidates = new Candidate[numberOfCandidates];
    }
    private void askNamesOfCandidates() { // 후보자 이름을 입력받아서 배열에 저장한다. 인원수가 입력되지 않은 상태면 인원수를 입력하게 한다. 이름이 유효하지 않으면 재입력하게 한다.
        if (candidates == null) {
            askNumberOfCandidates();
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
    }
    // 후보자를 입력받아 해당 후보자의 득표율을 가져온다.
    private double getVoteShare(Candidate candidate) {
        return candidate.getNumberOfVotes()*100./totalOfVotes;
    }
    // 후보자중 1명에게 랜덤하게 1표를 투표하고 표를 얻은 후보자를 반환한다.
    private Candidate doVote() {
        Random random = new Random();
        int candidateNumber = random.nextInt(candidates.length) + 1;
        candidates[candidateNumber - 1].addNumberOfVotes(1);
        numberOfVotes++;
        return candidates[candidateNumber - 1];
    }
    // 투표를 하고 투표 현황을 그린다.
    private void curVote() {
        Candidate candidate = doVote();
        System.out.printf("[투표진행율]: %.2f%%, %d명 투표 ==> %s\n", numberOfVotes*100./totalOfVotes, numberOfVotes, candidate.getName());
    }
    // 특정 후보의 정보를 화면에 출력한다.
    private void printCandidate(Candidate candidate) {
        int nameStrLength = 10 - candidate.getName().length();
        System.out.printf("[기호:%d] %-"+ nameStrLength +"s %5.2f%% (투표수: %d)\n",candidate.getCandidateNumber(), candidate.getName()+":",getVoteShare(candidate),candidate.getNumberOfVotes());
    }

    // 후보들의 정보를 화면에 출력한다.
    private void printCandidates() {
        for (Candidate candidate:candidates) {
            printCandidate(candidate);
        }
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

    void printAllVote() { // 투표를 시작해서 끝까지 화면에 그린다.
        for (int i = 0; i < totalOfVotes; i++) {
            curVote();
            printCandidates();
            System.out.println();
        }
        printElectee();
    }
}
