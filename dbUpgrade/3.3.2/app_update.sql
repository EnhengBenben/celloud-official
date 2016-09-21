/*!40101 SET NAMES utf8 */;

update tb_app set  app_doc = '软件是利用Miseq测序平台来筛查细胞染色体拷贝数异常的分析软件。<br />软件是针对对囊胚期细胞DNA经过Sureplex扩增利用Versiq建库由Miseq测序平台测序所得到的DNA数据所设计的分析胚胎染色体拷贝数异常的分析软件，支持BAM文件和fastq文件。软件的核心算法包括隐马尔科夫模型（HMM）和核密度估计（KDE），CNV检出的分辨率为4M，并且能够筛查出存在嵌合细胞。' where app_id=119;
update tb_app set  app_doc = '软件是针对囊胚期细胞DNA经过MDA扩增利用Vazyme建库并且由Miseq测序平台测序所得到的DNA数据所设计的分析胚胎染色体拷贝数异常的分析软件，支持BAM文件和fastq文件。软件的核心算法包括隐马尔科夫模型（HMM）和核密度估计（KDE），CNV检出的分辨率为4M，并且能够筛查出存在嵌合细胞。' where app_id=124;
update tb_app set  app_doc = '软件是针对组织中提取的DNA利用Vazyme建库由Miseq测序平台测序所得到的DNA数据所设计的分析胚胎染色体拷贝数异常的分析软件，支持BAM文件和fastq文件。软件的核心算法包括隐马尔科夫模型（HMM）和核密度估计（KDE），CNV检出的分辨率为4M，并且能够筛查出存在嵌合细胞。' where app_id=125;

update tb_app set  picture_name = 'rocky-min.png' where app_id=123;
