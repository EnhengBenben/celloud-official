package com.celloud.backstage.utils;

import java.awt.Color;
import java.awt.Font;
import java.awt.RenderingHints;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.CategoryAxis;
import org.jfree.chart.axis.ValueAxis;
import org.jfree.chart.labels.StandardCategoryItemLabelGenerator;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.renderer.category.BarRenderer;
import org.jfree.chart.title.TextTitle;
import org.jfree.data.category.CategoryDataset;

/** 
 * @author MQ: 
 * @date 2016年8月9日 下午4:54:15 
 * @description 
 */
public class JFreeChartUtil {
    /**
     * 
     * @author MQ
     * @date 2016年8月9日下午5:05:01
     * @description 画一个垂直方向的柱状图
     * @param title
     *            标题名称
     * @param categoryAxisLabel
     *            横坐标名称
     * @param valueAxisLabel
     *            纵坐标名称
     * @param dataset
     *            数据集
     * @param picPath
     *            文件保存路径
     * @param width
     *            宽
     * @param height
     *            高
     *
     */
    @SuppressWarnings("deprecation")
    public static void drawVerticalBarChart(String title, String categoryAxisLabel, String valueAxisLabel,
            CategoryDataset dataset, String picPath, int width, int height) {
        OutputStream out = null;
        try {
            // 创建一个图标
            JFreeChart chart = ChartFactory.createBarChart(title, categoryAxisLabel, valueAxisLabel,
                    dataset, PlotOrientation.VERTICAL,
                    false, true, false);
            /*---------------------------------------------------------------------*/
            // 获取中间区域
            CategoryPlot plot = chart.getCategoryPlot();
            // 中间区域背景色
            plot.setBackgroundPaint(Color.WHITE);
            // 中间区域纵轴网格线颜色
            plot.setRangeGridlinePaint(Color.GRAY);
            // 横轴
            CategoryAxis domainAxis = plot.getDomainAxis();
            // 纵轴
            ValueAxis rAxis = plot.getRangeAxis();
            /*----------设置消除字体的锯齿渲染（解决中文问题）--------------*/
            chart.getRenderingHints().put(RenderingHints.KEY_TEXT_ANTIALIASING,
                    RenderingHints.VALUE_TEXT_ANTIALIAS_OFF);
            /*----------设置标题字体--------------------------*/
            TextTitle textTitle = chart.getTitle();
            textTitle.setFont(new Font("宋体", Font.PLAIN, 24));
            /*------设置X轴坐标上的文字-----------*/
            domainAxis.setTickLabelFont(new Font("sans-serif", Font.PLAIN, 14));
            /*------设置X轴的标题文字------------*/
            domainAxis.setLabelFont(new Font("宋体", Font.BOLD, 14));
            /*------设置Y轴坐标上的文字-----------*/
            rAxis.setTickLabelFont(new Font("sans-serif", Font.PLAIN, 16));
            /*------设置Y轴的标题文字------------*/
            rAxis.setLabelFont(new Font("宋体", Font.BOLD, 16));

            /* 坐标轴对齐 */

            // 设置柱状图宽度和颜色
            BarRenderer barRenderer = new BarRenderer();
            float[] hsbColor = new float[3];
            Color.RGBtoHSB(79, 129, 189, hsbColor);
            barRenderer.setPaint(Color.getHSBColor(hsbColor[0], hsbColor[1], hsbColor[2]));
            barRenderer.setDrawBarOutline(true);
            barRenderer.setItemMargin(0);
            // 柱子上显示相应信息
            barRenderer.setBaseItemLabelGenerator(new StandardCategoryItemLabelGenerator());
            barRenderer.setBaseItemLabelsVisible(true);
            barRenderer.setBasePaint(Color.BLACK);
            barRenderer.setBaseItemLabelFont(new Font("宋体", Font.BOLD, 12));
            barRenderer.setMaximumBarWidth(0.1);
            plot.setRenderer(barRenderer);
            
            out = new FileOutputStream(picPath);// 图片是文件格式的，故要用到FileOutputStream用来输出。
            ChartUtilities.writeChartAsJPEG(out, chart, width, height);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (out != null) {
                    out.close();
                }
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    }

    /**
     * 
     * @author MQ
     * @date 2016年8月9日下午5:05:01
     * @description 画一个水平方向的柱状图
     * @param title
     *            标题名称
     * @param categoryAxisLabel
     *            横坐标名称
     * @param valueAxisLabel
     *            纵坐标名称
     * @param dataset
     *            数据集
     * @param picPath
     *            文件保存路径
     * @param width
     *            宽
     * @param height
     *            高
     *
     */
    @SuppressWarnings("deprecation")
    public static void drawHorizontalBarChart(String title, String categoryAxisLabel, String valueAxisLabel,
            CategoryDataset dataset, String picPath, int width, int height) {
        OutputStream out = null;
        try {
            // 创建一个图标
            JFreeChart chart = ChartFactory.createBarChart(title, categoryAxisLabel, valueAxisLabel, dataset,
                    PlotOrientation.HORIZONTAL, false, true, false);
            /*---------------------------------------------------------------------*/
            // 获取中间区域
            CategoryPlot plot = chart.getCategoryPlot();
            // 中间区域背景色
            plot.setBackgroundPaint(Color.WHITE);
            // 中间区域纵轴网格线颜色
            plot.setRangeGridlinePaint(Color.GRAY);
            // 横轴
            CategoryAxis domainAxis = plot.getDomainAxis();
            // 纵轴
            ValueAxis rAxis = plot.getRangeAxis();
            /*----------设置消除字体的锯齿渲染（解决中文问题）--------------*/
            chart.getRenderingHints().put(RenderingHints.KEY_TEXT_ANTIALIASING,
                    RenderingHints.VALUE_TEXT_ANTIALIAS_OFF);
            /*----------设置标题字体--------------------------*/
            TextTitle textTitle = chart.getTitle();
            textTitle.setFont(new Font("宋体", Font.PLAIN, 24));
            /*------设置X轴坐标上的文字-----------*/
            domainAxis.setTickLabelFont(new Font("sans-serif", Font.PLAIN, 14));
            /*------设置X轴的标题文字------------*/
            domainAxis.setLabelFont(new Font("宋体", Font.BOLD, 14));
            /*------设置Y轴坐标上的文字-----------*/
            rAxis.setTickLabelFont(new Font("sans-serif", Font.PLAIN, 16));
            /*------设置Y轴的标题文字------------*/
            rAxis.setLabelFont(new Font("宋体", Font.BOLD, 16));
            // 设置柱状图宽度和颜色
            BarRenderer barRenderer = new BarRenderer();
            float[] hsbColor = new float[3];
            Color.RGBtoHSB(79, 129, 189, hsbColor);
            barRenderer.setPaint(Color.getHSBColor(hsbColor[0], hsbColor[1], hsbColor[2]));
            barRenderer.setDrawBarOutline(true);
            barRenderer.setItemMargin(0);
            // 柱子上显示相应信息
            barRenderer.setBaseItemLabelGenerator(new StandardCategoryItemLabelGenerator());
            barRenderer.setBaseItemLabelsVisible(true);
            barRenderer.setBasePaint(Color.BLACK);
            barRenderer.setBaseItemLabelFont(new Font("宋体", Font.BOLD, 12));
            barRenderer.setMaximumBarWidth(0.1);
            // barRenderer.setBaseNegativeItemLabelPosition(
            // new ItemLabelPosition(ItemLabelAnchor.CENTER,
            // TextAnchor.BASELINE_RIGHT));
            // barRenderer.setItemLabelAnchorOffset(10);
            plot.setRenderer(barRenderer);

            out = new FileOutputStream(picPath);// 图片是文件格式的，故要用到FileOutputStream用来输出。
            ChartUtilities.writeChartAsJPEG(out, chart, width, height);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (out != null) {
                    out.close();
                }
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    }

    public static int getWidth(int dataLength) {
        if(dataLength==1){
            return 400;
        }else if(dataLength<3){
            return 600;
        }else if(dataLength<5){
            return 800;
        }else{
            return 1024;
        }
    }

    public static int getHeight(int dataLength) {
        if(dataLength==1){
            return 400;
        }else if(dataLength<3){
            return 600;
        }else if(dataLength<5){
            return 800;
        }else{
            return 1024;
        }
    }


}
