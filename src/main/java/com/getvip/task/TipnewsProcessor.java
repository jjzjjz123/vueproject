package com.getvip.task;


import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;


import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.processor.PageProcessor;
import us.codecraft.webmagic.selector.Html;
import us.codecraft.webmagic.selector.Selectable;

@Component
public class TipnewsProcessor implements PageProcessor {
	private int i =1;
    @Override
	public void process(Page page) {
    	
		List<Selectable> nodes = page.getHtml().xpath("/html/body/div[5]/ul/li/span[2]/a/@href").nodes();

		if (nodes.size() == 0) {
			saveVd(page);
		}
		//主要进入else的都是page页
		else 
		{
			for (Selectable selectable : nodes) {
				String url1 = selectable.toString();
				String url2="http://zuidazy2.com";
				//每天视频需要爬取详情页得链接
				String url3=url1+url2;
				page.addTargetRequest(url3);
			}
		
		}

	}
	
    //视频详情页内容
	private void saveVd(Page page) {
//		Video video=new Video();
//		
//		Html html = page.getHtml();
//		String title =html.xpath("//div[5]//h2/text()").toString();
//		String subtitle =html.xpath("//div[2]/div[1]/span/text()").toString();
//		String leixing =html.xpath("//div[2]/ul/li[4]/span/text()").toString();
//		String updatetime =html.xpath("//div[2]/ul/li[9]/span/text()").toString();
//		String info =html.xpath("//div[2]/ul/li[14]/div/span[2]/text()").toString();
//		String imgurl =html.xpath("//div[1]/img/@src").toString();
//		String uuid = UUID.randomUUID().toString().replaceAll("-","");
//		
//		video.setId(uuid);
//		video.setTitle(title);
//		video.setSubtitle(subtitle);
//		video.setLeixing(leixing);
//		video.setUpdatetime(updatetime);
//		video.setInfo(info);
//		video.setImgurl(imgurl);
//		
//		//播放类型：zuidam3u8
//	    List<Selectable> nodes1 = html.xpath("//div[3]/div[2]/div/div[1]/ul/li/text()").nodes();
//	   String jishuurl="";
//		for (Selectable selectable : nodes1) {
//			String play1 =selectable.toString();
//			jishuurl=jishuurl.concat(play1).concat("#");
//		}
//		//播放类型：zuidall
//		List<Selectable> nodes2 = html.xpath("//div[5]/div[3]/div[2]/div/div[2]/ul/li/text()").nodes();
//		for (Selectable selectable : nodes2) {
//			String play2 =selectable.toString();
//			jishuurl=jishuurl.concat(play2).concat("#");
//		}
//		//下载类型：迅雷下载
//		List<Selectable> nodes3 = html.xpath("//div[5]/div[4]/div[2]/div/div/ul/li/text()").nodes();
//		for (Selectable selectable : nodes3) {
//			String play3 =selectable.toString();
//			jishuurl=jishuurl.concat(play3).concat("#");
//		}
//		video.setJishuurl(jishuurl);
//		page.putField("video", video);
		
	}

	
	
	
	
	
	
	
	
	private Site site = Site.me().setCharset("utf8") // 设置编码
			.setTimeOut(10000) // 设置超时时间，单位是ms毫秒
			.setRetrySleepTime(3000) // 设置重试的间隔时间
			.setSleepTime(3) // 设置重试次数
			.addHeader("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64; rv:71.0) Gecko/20100101 Firefox/71.0")
			//.addHeader("Accept-Language", "zh-CN,zh;q=0.8,zh-TW;q=0.7,zh-HK;q=0.5,en-US;q=0.3,en;q=0.2")
			//.addHeader("Host", "img.alicdn.com")
	;

	public Site getSite() {
		return site;
	}

	@Autowired
	private SpringDataPipeline springDataPipeline;

	@Scheduled(fixedDelay = 1000*60*60)
	public void process() {
		Spider spider = Spider.create(new TipnewsProcessor()).addUrl("http://zuidazy3.com/?m=vod-index-pg-1.html");
		Spider spider2 = spider.thread(100);
		spider2.addPipeline(springDataPipeline).run();
		spider2.run();
			
	}

}
