package ir.ac.kntu;

import java.util.List;

public class Pagination<T> {
    private int pageSize;
    private int currentPage;
    private List<T> list;

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


    public Pagination(List<T> list, int pageSize) {
        this.list = list;
        setPageSize(pageSize);
        setCurrentPage(0);
    }

    public void showPage() {
        int start = currentPage * pageSize;
        int end = Math.min((currentPage + 1) * pageSize, list.size());
        for (int index = start; index < end; index++) {
            int num = index+1;
            System.out.println(ColorConsole.PINK + num + " ." + ColorConsole.PURPLE + list.get(index).toString() + ColorConsole.PURPLE);
        }
    }

    public void changePage(String command) {
        if ("next".equalsIgnoreCase(command) && (currentPage + 1) * pageSize < list.size()) {
            currentPage++;
        } else if ("previous".equalsIgnoreCase(command) && currentPage > 0) {
            currentPage--;
        } else if ("next".equalsIgnoreCase(command)){
            System.out.println(ColorConsole.RED + "No more page!This is the last Page!" + ColorConsole.RESET);
        } else if ("previous".equalsIgnoreCase(command)){
            System.out.println(ColorConsole.RED + "No more page!This is the first Page!" + ColorConsole.RESET);
        }
    }
}
