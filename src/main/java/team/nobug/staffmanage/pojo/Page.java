package team.nobug.staffmanage.pojo;

public class Page {

	private int currentPage;

	private int totalPages;

	private int pageSize;

	public Page(int currentPage, int pageSize) {
		this.currentPage = currentPage;
		this.pageSize = pageSize;
	}

	public Page() {
	}

	public int getTotalPages() {
		return totalPages;
	}

	public void setTotalPages(int totalPages) {
		
		/*本次分页插件不需要算
		 * if(totalPages % this.pageSize == 0) {
			totalPages = totalPages / this.pageSize;
		}else {
			totalPages = totalPages / this.pageSize + 1;
		}*/
		
		this.totalPages = totalPages;
	}

	public int getCurrentPage() {
		return currentPage;
	}

	public void setCurrentPage(int currentPage) {
		this.currentPage = currentPage;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

}
