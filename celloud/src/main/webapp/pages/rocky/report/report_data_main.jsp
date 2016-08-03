<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<div class="content">
	<div class="report-content">
		<h4>检测结果：</h4>
		<section>
			<div class="result-div">
				<p class="main">
					${rocky.records.size()<=0?'未':'' }发现<strong>致病相关</strong>突变！
				</p>
				<p class="notice">注： 本报告中&nbsp;“致病相关”&nbsp;是指本系统采集到的已知研究结论中，受检人样本中存在的突变与疾病有相关性。</p>
			</div>
		</section>
		<h4>结果说明：</h4>
		<div class="report-result">
			<section>
				<p>
					<strong>变异列表</strong>
				</p>
				<table class="table table-main -report">
					<thead>
						<tr>
							<th>编号</th>
							<th>基因</th>
							<th>染色体位置</th>
							<th>核苷酸变异</th>
							<th>氨基酸变异</th>
							<th>临床意义</th>
						</tr>
					</thead>
					<tbody>
						<c:forEach items="${rocky.records }" var="record" varStatus="status">
							<tr>
								<td>${status.count }</td>
								<td>${record.gene }</td>
								<td>${record.chromosome}</td>
								<td>${record.acids }</td>
								<td>${record.nucleotides }</td>
								<td>${record.significance }</td>
							</tr>
						</c:forEach>
					</tbody>
				</table>
			</section>
			<section>
				<p>
					<strong>变异描述</strong>
				</p>
				<table class="table table-main -report">
					<tbody>
						<c:forEach items="${rocky.records }" var="record">
							<tr>
								<td style="width: 150px;">${record.gene }:&nbsp;${record.acids }</td>
								<td>${record.description }</td>
							</tr>
						</c:forEach>
					</tbody>
				</table>
			</section>
			<section class="paragraph">
				<p>
					<strong>临床意义分类及说明</strong>
				</p>
				<p>参考国际癌症研究机构（International Agency for Research on Cancer，IARC）的五级分类标准，Celloud将基因变异的临床意义分为：</p>
				<dl>
					<dt>
						<label style="width: 150px">5 -- Pathogenic</label>
						致病的
					</dt>
					<dt>
						<label style="width: 150px">4 -- Likely pathogenic</label>
						可能致病的
					</dt>
					<dt>
						<label style="width: 150px">3 -- Uncertain</label>
						致病性不明确的
					</dt>
					<dt>
						<label style="width: 150px">2 -- Likely benign</label>
						可能良性的
					</dt>
					<dt>
						<label style="width: 150px">1 -- Benign</label>
						良性的
					</dt>
				</dl>
			</section>
			<section class="paragraph">
				<p>
					<strong>5、Pathogenic Variants 致病突变</strong>
				</p>
				<p class="notice">根据已有报道知道该突变导致基因功能障碍，或者根据该突变导致蛋白功能缺失、影响蛋白相互作用而推测为致病突变。已知致病突变“已知”致病必须有充足的证据，包括：</p>
				<dl>
					<dt>a、文献中报道在某一特定种群的致病突变（如奠基者致病突变founder pathogenic variants）</dt>
					<dt>b、在不同临床报道中不同患者的共同突变</dt>
					<dt>c、不同家族中的基因分离</dt>
					<dt>d、文献中的功能研究阐明：</dt>
					<dd>a、蛋白功能缺失</dd>
					<dd>b、蛋白功能加强</dd>
					<dd>c、功能芯片中显示剪切异常</dd>
					<dt>e、报道功能缺失突变明确与临床相关（包括移码突变、无义突变、剪切连接区突变）</dt>
				</dl>
			</section>
			<section class="paragraph">
				<p>
					<strong>4、Likely Pathogenic Variants 可能致病突变</strong>
				</p>
				<p>可能致病突变是根据其他已知致病突变预测可能致病。可能致病突变有多种证据可支持其致病性，包括：</p>
				<dl>
					<dt>a、突变发生在功能结构域中，或者是文献及已知致病突变证实的热点突变位置</dt>
					<dt>b、突变发生在已知致病突变的氨基酸残基中</dt>
					<dt>c、基于家族基因型分离研究或者认为/已知新突变与疾病相关</dt>
					<dt>d、预测为高度保守的碱基或氨基酸并被软件预测为致病</dt>
					<dt>e、无或者极低的等位基于频率与良性表型相关</dt>
					<dt>f、在特异常染色体隐性遗传疾病患者中发现与已知致病突变发生反应的突变，或其他支持测试（如生化检测）</dt>
				</dl>
			</section>
			<section class="paragraph">
				<p>
					<strong>3、Variants of Unknown(Uncertain) Significance 未知突变</strong>
				</p>
				<p>未知突变或意义不明突变是没有充分证据，或者证据发生明显冲突，而不能确定是可能致病还是良性突变。证据不充分或者有冲突，包括以下几个方面：</p>
				<dl>
					<dt>a、不同功能分析显示相反的结果</dt>
					<dt>b、与普遍表型相冲突的分离结果</dt>
					<dt>c、在单独分析的疾病中较低的基因频率（如偏低的外显率，非孟德尔遗传等）</dt>
					<dt>d、位于功能尚不明确的区域</dt>
					<dt>e、缺乏充足的临床案例</dt>
					<dt>f、缺乏足够的突变与临床表型相关的数据/证据</dt>
				</dl>
			</section>
			<section class="paragraph">
				<p>
					<strong>2、Likely Benign Variants 可能良性突变</strong>
				</p>
				<p>可能良性突变是根据其他已知良性突变推测为可能产生良性影响的突变。可能良性突变有多种证据可支持其良性影响，包括：</p>
				<dl>
					<dt>a、突变发生的位置不处于蛋白已知突变致病区域，或者文献中指出该突变是机体可接受的</dt>
					<dt>b、在基于家族间基因型分离的研究中未发现与疾病有明显相关性</dt>
					<dt>c、不处于进化保守区域或软件预测为良性</dt>
					<dt>d、在特定种族人群中基因频率低，在人群数据库中没有较大意义</dt>
					<dt>e、该突变与其他已知造成疾病分子同时发现</dt>
					<dt>f、与疾病信息相关的突变在正常个体中被观察到</dt>
					<dt>g、发生在无已知功能的重复区域中的插入或缺失</dt>
				</dl>
			</section>
			<section class="paragraph">
				<p>
					<strong>1、Benign Variants 良性突变</strong>
				</p>
				<p>良性突变没有已知临床报道该突变引起特定遗传疾病。</p>
				<p>评估与病例相关的人群数据来进行良性分类。在400及以上个体中，常染色体显性或X-连锁隐性疾病等位基因频率>1%，或者常染色体隐性疾病等位该基因频率>3%，没有其他相反证据证明该基因致病的话，则认为该突变为良性突变。有相冲突的证据，则根据基因和疾病的信息重新评估。在稀有疾病中，等位基因频率可适当降低。</p>
				<p>在已有证据支持或计算机算法预测为良性影响时，即使缺乏独立人群数据，沉默突变及剪切连接位点外的内含子突变也被分类为良性突变。</p>
			</section>
		</div>
	</div>
</div>