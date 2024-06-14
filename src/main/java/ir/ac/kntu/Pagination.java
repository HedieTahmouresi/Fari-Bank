package ir.ac.kntu;

import java.util.List;

public class Pagination {
    private int pageSize;
    private int currentPage;
    private List<Object> list;

    private final Input input = new Input();

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public int getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }


    public Pagination(List<Object> list, int pageSize) {
        this.list = list;
        setPageSize(pageSize);
        setCurrentPage(0);
    }

    public void showPage() {
        int start = currentPage * pageSize;
        int end = Math.min((currentPage + 1) * pageSize, list.size());
        for (int index = start; index < end; index++) {
            System.out.println(ColorConsole.PINK + index + 1 + " ." + ColorConsole.PURPLE + list.get(index) + ColorConsole.PURPLE);
        }
    }

    public void changePage(String command) {
        if ("next".equalsIgnoreCase(command) && (currentPage + 1) * pageSize < list.size()) {
            currentPage++;
        } else if ("previous".equalsIgnoreCase(command) && currentPage > 0) {
            currentPage--;
        }
    }

    public void showList(List<Object> myList, int pageSize){
        Pagination paginator = new Pagination(myList, 3);
        String command;
        do {
            paginator.showPage();
            System.out.println("Enter 'next' to go to the next page, 'previous' to go back or 'exit' to quit:");
            command = input.nextLine();
            paginator.changePage(command);
        } while (!"exit".equalsIgnoreCase(command));
    }
}
