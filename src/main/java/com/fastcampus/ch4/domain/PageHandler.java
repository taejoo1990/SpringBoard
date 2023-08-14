package com.fastcampus.ch4.domain;

public class PageHandler {
    private int totalCnt; //総投稿数
    private int pageSize; //ページに割り振る投稿数
    private int naviSize = 10; //画面で見せるページの数
    private int totalPage; //総ページ数
    private int page;     //現在ページ番号
    private int beginPage; //画面に表示されるスタートページ
    private int endPage;  //画面に表示されるエンドページ
    private boolean showPrev; //前のページボタンを見せるかどうか
    private boolean showNext; //次のページボタンを見せるかどうか

    public PageHandler(int totalCnt, int page) {
        this(totalCnt,page,10);
    }

    public PageHandler(int totalCnt, int page,int pageSize) {
        this.totalCnt = totalCnt;
        this.pageSize = pageSize;
        this.page = page;

        totalPage = (int)Math.ceil(totalCnt / (double)pageSize);
        beginPage = (page-1)/naviSize*naviSize+1;
        endPage = Math.min(beginPage+naviSize-1,totalPage);
        showPrev = beginPage != 1;
        showNext = endPage != totalPage;
    }

    void print(){
        System.out.println("page = " + page);
        System.out.print(showPrev ? "[Prev]" :"");
        for(int i= beginPage; i<=endPage ; i++){
            System.out.print(i+" ");
        }
        System.out.print(showNext? " [Next]" : "");
        System.out.println();
    }

    public int getTotalCnt() {
        return totalCnt;
    }

    public void setTotalCnt(int totalCnt) {
        this.totalCnt = totalCnt;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public int getNaviSize() {
        return naviSize;
    }

    public void setNaviSize(int naviSize) {
        this.naviSize = naviSize;
    }

    public int getTotalPage() {
        return totalPage;
    }

    public void setTotalPage(int totalPage) {
        this.totalPage = totalPage;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getBeginPage() {
        return beginPage;
    }

    public void setBeginPage(int beginPage) {
        this.beginPage = beginPage;
    }

    public int getEndPage() {
        return endPage;
    }

    public void setEndPage(int endPage) {
        this.endPage = endPage;
    }

    public boolean isShowPrev() {
        return showPrev;
    }

    public void setShowPrev(boolean showPrev) {
        this.showPrev = showPrev;
    }

    public boolean isShowNext() {
        return showNext;
    }

    public void setShowNext(boolean showNext) {
        this.showNext = showNext;
    }

    @Override
    public String toString() {
        return "PageHandler{" +
                "totalCnt=" + totalCnt +
                ", pageSize=" + pageSize +
                ", naviSize=" + naviSize +
                ", totalPage=" + totalPage +
                ", page=" + page +
                ", beginPage=" + beginPage +
                ", endPage=" + endPage +
                ", showPrev=" + showPrev +
                ", showNext=" + showNext +
                '}';
    }
}
