package cn.blmdz.captcha;
import java.awt.Color;
import java.io.IOException;
import java.util.Random;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.github.bingoohuang.patchca.background.MyCustomBackgroundFactory;
import com.github.bingoohuang.patchca.color.ColorFactory;
import com.github.bingoohuang.patchca.custom.ConfigurableCaptchaService;
import com.github.bingoohuang.patchca.filter.predefined.CurvesRippleFilterFactory;
import com.github.bingoohuang.patchca.filter.predefined.DiffuseRippleFilterFactory;
import com.github.bingoohuang.patchca.filter.predefined.DoubleRippleFilterFactory;
import com.github.bingoohuang.patchca.filter.predefined.MarbleRippleFilterFactory;
import com.github.bingoohuang.patchca.filter.predefined.WobbleRippleFilterFactory;
import com.github.bingoohuang.patchca.font.RandomFontFactory;
import com.github.bingoohuang.patchca.text.renderer.BestFitTextRenderer;
import com.github.bingoohuang.patchca.utils.encoder.EncoderHelper;
import com.github.bingoohuang.patchca.word.RandomWordFactory;

/**
 * 生成多彩验证码
 */
@Controller
public class Captcha {
    private static ConfigurableCaptchaService cs = new ConfigurableCaptchaService();
    public static final String TOKEN = "captchaToken";
    private static Random random = new Random();
    static {
        cs.setColorFactory(new ColorFactory() {
            @Override
            public Color getColor(int x) {
                int[] c = new int[3];
                int i = random.nextInt(c.length);
                for (int fi = 0; fi < c.length; fi++) {
                    if (fi == i) {
                        c[fi] = random.nextInt(71);
                    } else {
                        c[fi] = random.nextInt(256);
                    }
                }
                return new Color(c[0], c[1], c[2]);
            }
        });//颜色创建工厂
        RandomWordFactory wf = new RandomWordFactory();
        wf.setCharacters("23456789abcdefghigkmnpqrstuvwxyzABCDEFGHIGKLMNPQRSTUVWXYZ");
        wf.setMinLength(4);
        wf.setMaxLength(4);
        cs.setWordFactory(wf);//字符生成器
        cs.setBackgroundFactory(new MyCustomBackgroundFactory());//验证码图片背景
        cs.setTextRenderer(new BestFitTextRenderer());//文字渲染器设置
        cs.setFontFactory(new RandomFontFactory());//字体生成器
        cs.setWidth(100);
        cs.setHeight(50);
    }
    
    @RequestMapping(value="/api/img", method=RequestMethod.GET, produces=MediaType.APPLICATION_JSON_VALUE)
    public void img(HttpServletRequest request, HttpServletResponse response) throws IOException {

        switch (random.nextInt(5)) {
	        case 0:
	            cs.setFilterFactory(new CurvesRippleFilterFactory(cs.getColorFactory()));
	            break;
	        case 1:
	            cs.setFilterFactory(new MarbleRippleFilterFactory());
	            break;
	        case 2:
	            cs.setFilterFactory(new DoubleRippleFilterFactory());
	            break;
	        case 3:
	            cs.setFilterFactory(new WobbleRippleFilterFactory());
	            break;
	        case 4:
	            cs.setFilterFactory(new DiffuseRippleFilterFactory());
	            break;
        }//图片滤镜设置
        
        HttpSession session = request.getSession();
        
        String token = EncoderHelper.getChallangeAndWriteImage(cs, "png", response.getOutputStream());
        session.setAttribute(TOKEN, token.toLowerCase());
        
        System.out.println("当前的SessionID=" + session.getId() + "，验证码=" + token.toLowerCase());
    }
}