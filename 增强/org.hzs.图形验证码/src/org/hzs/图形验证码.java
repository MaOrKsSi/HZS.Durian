package org.hzs;

import java.io.IOException;

/**
 * 验证码生成器
 *
 * @author dsna
 *
 */
public class 图形验证码 {
    // 图片的宽度。  

    private int width = 250;
    // 图片的高度。  
    private int height = 60;
    // 验证码字符个数  
    private int codeCount = 5;
    // 验证码干扰线数  
    private int lineCount = 100;
    // 验证码  
    private String code = null;
    // 验证码图片Buffer  
    private java.awt.image.BufferedImage buffImg = null;
    //'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'J', 'K', 'L', 'M', 'N', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z',
    //'@', '#', '%', '&',   
    //'0', '1', '2', '4', '5', '6', '7', '8', '9',
    private final char[] codeSequence = {'乙', '二', '十', '丁', '厂', '七', '卜', '人', '入', '八', '九', '几', '儿', '了', '力', '乃', '刀', '又', '三', '干', '亏', '工', '土', '才', '寸', '下', '大', '丈', '与', '万', '上', '小', '口', '巾', '山', '乞', '川', '亿', '个', '勺', '久', '凡', '及', '夕', '丸', '么', '广', '亡', '门', '义', '之', '尸', '弓', '己', '子', '卫', '也', '女', '飞', '刃', '习', '叉', '马', '乡', '丰', '王', '井', '开', '夫', '天', '无', '元', '专', '云', '扎', '艺', '木', '五', '支', '厅', '不', '太', '犬', '区', '历', '尤', '友', '匹', '车', '巨', '牙', '屯', '比', '互', '切', '瓦', '止', '少', '日', '中', '冈', '贝', '内', '水', '见', '午', '牛', '手', '毛', '气', '升', '长', '仁', '什', '片', '仆', '化', '仇', '币', '仍', '仅', '斤', '爪', '反', '介', '父', '从', '今', '凶', '分', '乏', '公', '仓', '月', '氏', '勿', '欠', '风', '丹', '匀', '乌', '凤', '勾', '文', '六', '方', '火', '为', '斗', '忆', '订', '计', '户', '认', '心', '尺', '引', '丑', '巴', '孔', '队', '办', '以', '允', '予', '劝', '双', '书', '幻', '玉', '刊', '示', '末', '击', '打', '巧', '正', '扑', '扒', '功', '扔', '去', '甘', '世', '古', '节', '本', '术', '可', '丙', '左', '厉', '右', '石', '布', '龙', '平', '灭', '轧', '东', '卡', '北', '占', '业', '旧', '帅', '归', '且', '旦', '目', '叶', '甲', '申', '叮', '电', '号', '田', '由', '史', '只', '央', '兄', '叼', '叫', '另', '叨', '叹', '四', '生', '失', '禾', '丘', '付', '仗', '代', '仙', '们', '仪', '白', '仔', '他', '斥', '瓜', '乎', '丛', '令', '用', '甩', '印', '乐', '句', '匆', '册', '犯', '外', '处', '冬', '鸟', '务', '包', '饥', '主', '市', '立', '闪', '兰', '半', '汁', '汇', '头', '汉', '宁', '穴', '它', '讨', '写', '让', '礼', '训', '必', '议', '讯', '记', '永', '司', '尼', '民', '出', '辽', '奶', '奴', '加', '召', '皮', '边', '发', '孕', '圣', '对', '台', '矛', '纠', '母', '幼', '丝', '式', '刑', '动', '扛', '寺', '吉', '扣', '考', '托', '老', '执', '巩', '圾', '扩', '扫', '地', '扬', '场', '耳', '共', '芒', '亚', '芝', '朽', '朴', '机', '权', '过', '臣', '再', '协', '西', '压', '厌', '在', '有', '百', '存', '而', '页', '匠', '夸', '夺', '灰', '达', '列', '死', '成', '夹', '轨', '邪', '划', '迈', '毕', '至', '此', '贞', '师', '尘', '尖', '劣', '光', '当', '早', '吐', '吓', '虫', '曲', '团', '同', '吊', '吃', '因', '吸', '吗', '屿', '帆', '岁', '回', '岂', '刚', '则', '肉', '网', '年', '朱', '先', '丢', '舌', '竹', '迁', '乔', '伟', '传', '乒', '乓', '休', '伍', '伏', '优', '伐', '延', '件', '任', '伤', '价', '份', '华', '仰', '仿', '伙', '伪', '自', '血', '向', '似', '后', '行', '舟', '全', '会', '杀', '合', '兆', '企', '众', '爷', '伞', '创', '肌', '朵', '杂', '危', '旬', '旨', '负', '各', '名', '多', '争', '色', '壮', '冲', '冰', '庄', '庆', '亦', '刘', '齐', '交', '次', '衣', '产', '决', '充', '妄', '闭', '问', '闯', '羊', '并', '关', '米', '灯', '州', '汗', '污', '江', '池', '汤', '忙', '兴', '宇', '守', '宅', '字', '安', '讲', '军', '许', '论', '农', '讽', '设', '访', '寻', '那', '迅', '尽', '导', '异', '孙', '阵', '阳', '收', '阶', '阴', '防', '奸', '如', '妇', '好', '她', '妈', '戏', '羽', '观', '欢', '买', '红', '纤', '级', '约', '纪', '驰', '巡'};

    public 图形验证码() {
//        this.createCode();
    }

    /**
     *
     * @param width 图片宽
     * @param height 图片高
     */
    public 图形验证码(int width, int height) {
        this.width = width;
        this.height = height;
//        this.createCode();
    }

    /**
     *
     * @param width 图片宽
     * @param height 图片高
     * @param codeCount 字符个数
     * @param lineCount 干扰线条数
     */
    public 图形验证码(int width, int height, int codeCount, int lineCount) {
        this.width = width;
        this.height = height;
        this.codeCount = codeCount;
        this.lineCount = lineCount;
//        this.createCode();
    }

    public void createCode() {
        int x = 0, fontHeight = 0, codeY = 0;
        int red = 0, green = 0, blue = 0;

        x = width / (codeCount + 2);//每个字符的宽度  
        fontHeight = height - 2;//字体的高度  
        codeY = height - 4;

        // 图像buffer  
        buffImg = new java.awt.image.BufferedImage(width, height, java.awt.image.BufferedImage.TYPE_INT_RGB);
        java.awt.Graphics2D g = buffImg.createGraphics();
        // 生成随机数  
        java.util.Random random = new java.util.Random();
        // 将图像填充为白色  
        g.setColor(java.awt.Color.WHITE);
        g.fillRect(0, 0, width, height);
        // 创建字体  
        java.awt.Font font = new java.awt.Font("迷你简谁的字", java.awt.Font.TRUETYPE_FONT, fontHeight);
        g.setFont(font);

        for (int i = 0; i < lineCount; i++) {
            int xs = random.nextInt(width);
            int ys = random.nextInt(height);
            int xe = xs + random.nextInt(width / 8);
            int ye = ys + random.nextInt(height / 8);
            red = random.nextInt(255);
            green = random.nextInt(255);
            blue = random.nextInt(255);
            g.setColor(new java.awt.Color(red, green, blue));
            g.drawLine(xs, ys, xe, ye);
        }

        // randomCode记录随机产生的验证码  
        StringBuilder randomCode = new StringBuilder();
        // 随机产生codeCount个字符的验证码。  
        for (int i = 0; i < codeCount; i++) {
            String strRand = String.valueOf(codeSequence[random.nextInt(codeSequence.length)]);
            // 产生随机的颜色值，让输出的每个字符的颜色值都将不同。  尽可能让字的颜色较深
            red = random.nextInt(160);
            green = random.nextInt(160);
            blue = random.nextInt(160);
            g.setColor(new java.awt.Color(red, green, blue));
            g.drawString(strRand, (i + 1) * x, codeY);
            // 将产生的四个随机数组合在一起。  
            randomCode.append(strRand);
        }
        // 将四位数字的验证码保存到Session中。  
        code = randomCode.toString();
    }

    public void createCode(final String ci_s) {
        int x = 0, fontHeight = 0, codeY = 0;
        int red = 0, green = 0, blue = 0;
        char[] ji_char = ci_s.toCharArray();

        x = width / (ji_char.length + 2);//每个字符的宽度  
        fontHeight = height - 2;//字体的高度  
        codeY = height - 4;

        // 图像buffer  
        buffImg = new java.awt.image.BufferedImage(width, height, java.awt.image.BufferedImage.TYPE_INT_RGB);
        java.awt.Graphics2D g = buffImg.createGraphics();
        // 生成随机数  
        java.util.Random random = new java.util.Random();
        // 将图像填充为白色  
        g.setColor(java.awt.Color.WHITE);
        g.fillRect(0, 0, width, height);
        // 创建字体  
        java.awt.Font font = new java.awt.Font("迷你简谁的字", java.awt.Font.TRUETYPE_FONT, fontHeight);
        g.setFont(font);

        for (int i = 0; i < lineCount; i++) {
            int xs = random.nextInt(width);
            int ys = random.nextInt(height);
            int xe = xs + random.nextInt(width / 8);
            int ye = ys + random.nextInt(height / 8);
            red = random.nextInt(255);
            green = random.nextInt(255);
            blue = random.nextInt(255);
            g.setColor(new java.awt.Color(red, green, blue));
            g.drawLine(xs, ys, xe, ye);
        }

        // randomCode记录随机产生的验证码  
        StringBuilder randomCode = new StringBuilder();
        // 随机产生codeCount个字符的验证码。  
        for (int i = 0; i < ji_char.length; i++) {
            String strRand = String.valueOf(ji_char[i]);
            // 产生随机的颜色值，让输出的每个字符的颜色值都将不同。  尽可能让字的颜色较深
            red = random.nextInt(160);
            green = random.nextInt(160);
            blue = random.nextInt(160);
            g.setColor(new java.awt.Color(red, green, blue));
            g.drawString(strRand, (i + 1) * x, codeY);
            // 将产生的四个随机数组合在一起。  
            randomCode.append(strRand);
        }
        // 将四位数字的验证码保存到Session中。  
        code = randomCode.toString();
    }

    public byte[] i_byteArray() throws IOException {
        java.io.ByteArrayOutputStream dd = new java.io.ByteArrayOutputStream();
        javax.imageio.ImageIO.write(buffImg, "png", dd);
        return dd.toByteArray();
    }

    public String getCode() {
        return code;
    }
//
//    public void write(java.io.OutputStream sos) throws IOException {
//        javax.imageio.ImageIO.write(buffImg, "png", sos);
//        sos.close();
//    }
//
//    public java.awt.image.BufferedImage getBuffImg() {
//        return buffImg;
//    }
}
