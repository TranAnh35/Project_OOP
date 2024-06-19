package demo;

public class SharedData {
    private static SharedData instance = new SharedData();
    private String selectedButton;
    private String classSectionID;

    private SharedData() {}

    public static SharedData getInstance() {
        return instance;
    }

    public String getSelectedButton() {
        return selectedButton;
    }

    public void setSelectedButton(String selectedButton) {
        this.selectedButton = selectedButton;
    }

    public String getClassSectionID() {
        return classSectionID;
    }

    public void setClassSectionID(String classSectionID) {
        this.classSectionID = classSectionID;
    }
}
