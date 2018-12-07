package org.apache.hadoop.mapreduce;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapred.JobConf;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.mapreduce.keyStatistic;
import org.apache.hadoop.util.GenericOptionsParser;

import java.io.IOException;
import java.util.StringTokenizer;

public class SecondRoundMapReduce {
    public static class SecondRoundMap
            extends Mapper<Object, Text, Text, IntWritable> {

        private IntWritable v= new IntWritable();
        private Text word = new Text();

        public void map(Object key, Text value, Context context
        ) throws IOException, InterruptedException {
                String [] row = value.toString().split("\t");
                word.set(row[0]);
                v = new IntWritable(Integer.parseInt(row[1]));
                context.write(word, v);
            }
        }

    public static class IntSumReducer
            extends Reducer<Text,IntWritable,Text,IntWritable> {
        private IntWritable result = new IntWritable();

        public void reduce(Text key, Iterable<IntWritable> values,
                           Context context
        ) throws IOException, InterruptedException {
            int sum = 0;
            for (IntWritable val : values) {
                sum += val.get();
            }
            result.set(sum);
            context.write(key, result);
        }
    }

    public static boolean run(Path path, JobContext jobContext, JobConf jobConf)throws Exception {
        keyStatistic.setSecondRound();
        Configuration conf = new Configuration(jobConf);
        System.out.println("DUBUDUBUDU");
        Job job = Job.getInstance(conf, "second");
        job.setJarByClass(SecondRoundMapReduce.class);
        job.setMapperClass(SecondRoundMap.class);
//        job.setCombinerClass(IntSumReducer.class);
//        job.setReducerClass(IntSumReducer.class);
//        job.setOutputKeyClass(Text.class);
//        job.setOutputValueClass(IntWritable.class);
            FileInputFormat.addInputPath(job, path);
        FileOutputFormat.setOutputPath(job,
               new Path(path.toString()+"/output2")) ;
        return job.waitForCompletion(true, 2);
    }
}
