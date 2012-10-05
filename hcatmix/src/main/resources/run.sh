#! /bin/sh


function get_jar_list {
    SEPARATOR=$1
    export CLASSPATH=
    export CLASSPATH=${CLASSPATH}${SEPARATOR}`find $HIVE_HOME/lib -type f -name "*.jar"| paste -d $SEPARATOR -s -`
    export CLASSPATH=${CLASSPATH}${SEPARATOR}`find $HIVE_HOME/conf | paste -d $SEPARATOR -s -`
    export CLASSPATH=${CLASSPATH}${SEPARATOR}`find $HADOOP_HOME/ -type f -name '*.jar' | paste -d $SEPARATOR -s -`
    export CLASSPATH=${CLASSPATH}${SEPARATOR}`find $PIG_HOME/lib/ -type f -name '*.jar' | paste -d $SEPARATOR -s -`
    echo $CLASSPATH
}
conf_dir="${HADOOP_CONF_DIR:-$HADOOP_HOME/conf}"
scripts=`dirname "$0"`
scripts=`cd $scripts/../../../; pwd`
hcatmixjar=$scripts/target/hcatmix-1.0-SNAPSHOT-jar-with-dependencies.jar

export HADOOP_CLASSPATH=`get_jar_list :`
export JAR_LIST=`get_jar_list ,`
hadoop  --config $conf_dir jar $hcatmixjar org.apache.hcatalog.hcatmix.load.HadoopLoadGenerator -libjars $JAR_LIST
#hadoop  org.apache.hcatalog.hcatmix.HCatMixSetup -f scripts/hcat_table_specification.xml -m 0 -o /tmp/hcatmix/