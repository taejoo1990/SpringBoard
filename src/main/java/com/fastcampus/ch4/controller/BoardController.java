package com.fastcampus.ch4.controller;

import com.fastcampus.ch4.domain.*;
import com.fastcampus.ch4.service.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.stereotype.*;
import org.springframework.ui.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.*;
import java.util.*;

@Controller
@RequestMapping("/board")
public class BoardController {
    @Autowired
    BoardService boardService;

    @GetMapping("/read")
    public String read(Integer bno, Model model){
        try {
            BoardDto boardDto =  boardService.read(bno);
            model.addAttribute(boardDto);
            model.addAttribute("mode","read");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return "board";
    }
    @PostMapping("/modify")
    public String modify(BoardDto boardDto ,Integer page, Integer pageSize, Model model){
        try {
            boardService.modify(boardDto);
            model.addAttribute("page",page);
            model.addAttribute("pageSize",pageSize);
            model.addAttribute("bno",boardDto.getBno());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return "redirect:/board/read";
    }
    @GetMapping("/write")
    public String write(Model model){
        model.addAttribute("mode","write");
        return "board";
    }
    @PostMapping("/write")
    public String writeThis(BoardDto boardDto,Model model, HttpSession session, RedirectAttributes rattr){
        String writer = (String)session.getAttribute("id");
        boardDto.setWriter(writer);
        try {
            int rowCnt = boardService.write(boardDto);
            if(rowCnt!=1){
                throw new Exception("write fail");
            }
            rattr.addFlashAttribute("msg","WRT_OK");
            return "redirect:/board/list";
        } catch (Exception e) {
            model.addAttribute(boardDto);
            model.addAttribute("msg","WRT_ERR");
            e.printStackTrace();
            return "board";
        }

    }

    @PostMapping("/remove")
    public String remove2(Integer bno, Integer page, Integer pageSize,Model model, HttpSession session, RedirectAttributes rattr){
        String writer = (String)session.getAttribute("id");
        try {
            int rowCnt = boardService.remove(bno,writer);
            if(rowCnt!=1) {
                throw new Exception("board remove err");
            }
            rattr.addFlashAttribute("msg","del_ok");
            return "redirect:/board/list?page="+page+"&pageSize="+pageSize;

        } catch (Exception e) {
            rattr.addFlashAttribute("msg","del_err");
        }
        return "redirect:/board/list";
    }
    @GetMapping("/list")
    public String list(Integer page, Integer pageSize, Model model, HttpServletRequest request) {
        if(!loginCheck(request))
            return "redirect:/login/login?toURL="+request.getRequestURL();  // 로그인을 안했으면 로그인 화면으로 이동
        page = page==null ? 1 : page;
        pageSize = pageSize==null ? 10 : pageSize;
        try {
            int totalCnt = boardService.getCount();
            PageHandler ph = new PageHandler(totalCnt,page,pageSize);
            Map map = new HashMap();
            map.put("offset",(page-1)*pageSize);
            map.put("pageSize",pageSize);
            List<BoardDto> list = boardService.getPage(map);
            model.addAttribute("list", list);
            model.addAttribute("ph",ph);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return "boardList"; // 로그인을 한 상태이면, 게시판 화면으로 이동
    }

    private boolean loginCheck(HttpServletRequest request) {
        // 1. 세션을 얻어서
        HttpSession session = request.getSession();
        // 2. 세션에 id가 있는지 확인, 있으면 true를 반환
        return session.getAttribute("id")!=null;
    }
}