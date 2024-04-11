package hrsystem.utils;

public class EntryDataValidator {

    /**
     * Constructor without parameters
     */
    public EntryDataValidator() {
    }

    /**
     *This function is checking if any of String parameters is null or empty
     * @param params - String parameters
     * @return true / false
     */
    public boolean isAnyParameterNullOrEmpty(String... params) {
        //using varargs make this working even when number of String parameters will be changed
        for (String param : params) {
            if (param == null || param.isEmpty()) {
                return true;
            }
        }
        return false;
    }
}