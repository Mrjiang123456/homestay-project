package com.example;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

import java.io.IOException;

public class MapReduceMain {

    private static String in = "/user/hive/warehouse/ods.db/minsu.txt";

    private static String out = "/user/hive/warehouse/dwd.db/ms";

    public static void main(String[] args) throws IOException, InterruptedException, ClassNotFoundException {

        if(args.length == 2){
            in = args[0];
            out = args[1];
        }

        System.setProperty("HADOOP_USER_NAME", "root");

        Configuration config = new Configuration();
        FileUtils.checkFileExists(config, new Path(out));

        Job job = Job.getInstance(config);

        job.setJarByClass(MapReduceMain.class);

        job.setMapperClass(msMapper.class);

        job.setOutputKeyClass(NullWritable.class);
        job.setOutputValueClass(Text.class);

        FileInputFormat.setInputPaths(job, new Path(in));
        FileOutputFormat.setOutputPath(job, new Path(out));

        job.waitForCompletion(true);
    }

    /**
     * 数据清洗
     */
    static class msMapper extends Mapper<LongWritable, Text, NullWritable, Text>{

        @Override
        protected void map(LongWritable key, Text value, Mapper<LongWritable, Text, NullWritable, Text>.Context context) throws IOException, InterruptedException {

            String line = value.toString();

            String[] fields = line.split("-");

            /**
             * 过滤掉异常数据
             */
            try {
                if (fields.length == 19) {
                    String productId = fields[0];
                    String cityName = fields[1];
                    String title = fields[2];
                    /**
                     * 去掉标题两边的空格
                     */
                    title = title.trim();

                    String districtName = fields[3];
                    String locationArea = fields[4];
                    String starRating = fields[5];
                    String starRatingDesc = fields[6];
                    String commentNumber = fields[7];
                    String distanceDesc = fields[8];

                    /**
                     * 去掉详细地址两边的空格
                     */
                    distanceDesc = distanceDesc.trim();
                    String coverImage = fields[9];
                    String favCount = fields[10];
                    String favCountDesc = fields[11];
                    String productUserCount = fields[12];
                    String consumeDesc = fields[13];
                    String discountPrice = fields[14];

                    /**
                     * 把价格字段转成新的格式
                     */
                    String price = Integer.parseInt(discountPrice) / 100 + "";

                    String layoutDesc = fields[15];

                    /**
                     * 去掉户型字段里面的脏数据
                     */
                    if("None".equals(layoutDesc)){
                        return;
                    }

                    String guestNumberDesc = fields[16];
                    String ugcDesc = fields[17];

                    /**
                     * 去掉用户评论两边的引号
                     */
                    ugcDesc = ugcDesc.replace("“", "").replace("”","");

                    String tags = fields[18];
                    /**
                     * 标签字段格式化
                     */
                    tags = tags.replace("/", "");

                    /**
                     * 以下字段必须是integer类型
                     */
                    Integer.parseInt(productId);
                    Integer.parseInt(starRating);
                    Integer.parseInt(favCount);
                    Integer.parseInt(productUserCount);
                    Integer.parseInt(commentNumber);

                    String output = productId + "-" + cityName + "-" + title + "-" + districtName + "-" + locationArea + "-" + starRating
                            + "-" + starRatingDesc + "-" + commentNumber + "-" + distanceDesc + "-" + coverImage + "-" + favCount + "-" + favCountDesc
                            + "-" + productUserCount + "-" + consumeDesc + "-" + price + "-" + layoutDesc + "-" + guestNumberDesc + "-" + ugcDesc + "-" + tags;

                    context.write(NullWritable.get(), new Text(output));

                }
            } catch (Exception ex) {
                System.out.println("异常数据：" + line);
            }
        }
    }
}
