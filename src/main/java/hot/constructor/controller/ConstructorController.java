package hot.constructor.controller;

import java.io.IOException;
import java.sql.Date;
import java.sql.Timestamp;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import hot.admin.service.OrderServiceImpl;
import hot.aws.S3Manager;
import hot.channel.domain.Channel;
import hot.channel.domain.FavoritePortfolio;
import hot.channel.repository.FavoritePortfolioRepository;
import hot.channel.service.ChannelServiceImpl;
import hot.constructor.service.ConstructorServiceImpl;
import hot.constructor.service.portfolioServiceImpl;
import hot.member.domain.Constructor;
import hot.member.domain.Order;
import hot.member.domain.Portfolio;
import hot.review.domain.Review;
import hot.review.service.ReviewServiceImpl;
import hot.security.CustomUser;
import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class ConstructorController {
	
	
	private final ChannelServiceImpl channelService;
	private final portfolioServiceImpl portfolioService;
	private final ConstructorServiceImpl constructorService;
	private final OrderServiceImpl orderService;
	private final FavoritePortfolioRepository favoritePortRep;
	private final ReviewServiceImpl reviewService;
	
	String orderMethod ;
	String orderStatus ;
	String orderPayment ;
	
	@Autowired
	S3Manager s3manager;
	
	@RequestMapping("/channel/constructor/portfolioForm")
	public ModelAndView portfolioForm() {
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();		
		Integer chNo = ((CustomUser)principal).getChNo();
		List<Portfolio> portList = portfolioService.selectPortfolioChNo(chNo);
		return new ModelAndView("/channel/constructor/portfolioForm","portList", portList);

	}
	
	@RequestMapping("/channel/constructor/myChannel")
	public ModelAndView myChannel(@RequestParam(defaultValue = "0")int nowPage, Model model) {
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();		
		//Integer chNo = ((CustomUser)principal).getChNo();
		//System.out.println(chNo);
		List<Portfolio> portList = portfolioService.selectPortfolioChNo(1);
		
		///////// chNo 다시 손볼 것 
		
		
		Channel channel = channelService.selectChannel(1);
		Pageable page =PageRequest.of(nowPage, 2, Direction.DESC, "reviewNo");
		Page<Review> pageReview = reviewService.selectAll(page, channel);

		model.addAttribute("list", pageReview.getContent());
		ModelAndView mv = new ModelAndView();
		mv.setViewName("/channel/constructor/myChannel");
		mv.addObject("channel", channel);
		mv.addObject("portList", portList);
		return mv;
	}
	
	
	
	@PostMapping("/channel/constructor/payment/complete")
	@ResponseBody
	public String insertPortfolio(String pg, String pay_method , String status) throws IOException{
		System.out.println("pay_method = "+pay_method);
		System.out.println("status = "+status);
		System.out.println("String = " + pg);
		
		
		String orderMethod = pay_method;;
		String orderStatus = status;;
		String orderPayment = pg;   
		
		return "결제 완료"; 
	}
	

	/* 
	 * 포트폴리오 등록
	 */
	
	@PostMapping("/channel/constructor/insertPort")
	public ModelAndView insertPortfolio2(String portTitle, 
			String portDescription, MultipartFile file, 
			Date portStartDate, Date portEndDate, String portImg, Integer chNo, 
			Order order,
			String pay_method, String status, Integer amount, String orderStatusName) throws IOException{	
		System.out.println("포트폴리오 등록 동작 컨트롤러 들어옴");
		System.out.println("pay_method: " + pay_method);
		System.out.println("status: " + status);
		System.out.println("amount: " + amount);

		//int ChannelNo = Integer.parseInt(chNo); 
		
		Long sd=portStartDate.getTime();
		Long ed=portEndDate.getTime();

		Timestamp startDate = new Timestamp(sd);
		Timestamp endDate = new Timestamp(ed);
		
		Portfolio portfolio = new Portfolio();
		//Channel channel = channelService.selectChannel(ChannelNo);
		System.out.println("chNo: " + chNo);
		Channel channel = channelService.selectChannel(chNo);
		System.out.println("channel.getChDescription(): " + channel.getChDescription());
		String imgpath = s3manager.saveUploadedFiles(file);
		
		portfolio.setPortTitle(portTitle);
		portfolio.setPortDescription(portDescription);
		portfolio.setPortStartDate(startDate);
		portfolio.setPortEndDate(endDate);
		portfolio.setChannel(channel);
		portfolio.setPortImg(imgpath);
		
		
		Object principal  = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		CustomUser customUser = (CustomUser)principal;
		
		Integer memberNo = customUser.getMemberNo();
		portfolioService.insertPortfolio(portfolio);
		Constructor constructor = constructorService.selectConstructor(memberNo);
		
		//int orderStatus = Integer.parseInt(status);
		
		order.setConstructor(constructor);
		order.setPortfolio(portfolio);
		order.setOrderMethod(pay_method);
		order.setOrderPayment(amount);
		
		System.out.println("getOrderPayment: " + order.getOrderPayment());
		
		System.out.println("status1: " + order.getOrderStatus());
		
		portfolioService.insertOrder(order, orderStatusName);
		
		System.out.println("status2: " + order.getOrderStatus());
		
		System.out.println("다 된건가 ....");
		// chNo 채널별 포트폴리오 검색
		
		//List<Portfolio> portlist = portfolioService.selectPortfolioChNo(ChannelNo);
		List<Portfolio> portlist = portfolioService.selectPortfolioChNo(chNo);
		System.out.println("포트폴리오 : " + portlist.size());
		
		return new ModelAndView("redirect:/channel/guest/channelDetail/"+chNo, "portlist", portlist); 
	}
	
	////////////////////////////////////////////////////////////
	
	
	@RequestMapping("/channel/constructor/payment/inputForm")
	public void inputForm() {
		
		
		//return "channel/constructor/payment/inputForm";
	}
	
	
	
	@RequestMapping("/channel/constructor/payment/importApi")
	public String payment() {
		System.out.println("결제 api 들어옴");
		
		return "channel/constructor/payment/importApi";
	}
	
	
	
	@ResponseBody
	@RequestMapping("/payments/complete")
	public String paymentComplete() {
		System.out.println(11111);
		
		return "success";
	}
	
	@RequestMapping("/errormessage")
	public String error() {
		
		
		return "error/error";
	}
	
	/**
	 * 포트폴리오 전체 목록
	 * */
	@RequestMapping("/channel/guest/portfolioAll")
	public ModelAndView portfolioList(@RequestParam(defaultValue = "0")int nowPage) {
		
		Pageable page =PageRequest.of(nowPage, 6, Direction.DESC, "portNo");
		Page<Portfolio> portList = portfolioService.findAllPortfolio(page);
		
		ModelAndView mv = new ModelAndView();
		mv.setViewName("channel/guest/portfolioAll");
		mv.addObject("portList", portList.getContent());
		mv.addObject("totalPage", portList.getTotalPages());
		mv.addObject("nowPageNum", portList.getNumber());
		
		return mv;
	}
	
	/**
	 * 포트폴리오 상세 페이지
	 * */
	@RequestMapping("/channel/guest/portfolioDetail/{portNo}")
	public ModelAndView portfolioDetail(@PathVariable(name="portNo")int portNo) {
		
		Portfolio port = portfolioService.portfolioDetail(portNo);
		
		List<FavoritePortfolio> favPort = favoritePortRep.findByPortfolio(port);
		
		ModelAndView mv = new ModelAndView();
		mv.setViewName("channel/guest/portfolioDetail");
		mv.addObject("port", port);
		mv.addObject("favPort", favPort);
		
		return mv;
	}
	
	
	
}