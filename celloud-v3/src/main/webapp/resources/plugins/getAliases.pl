use File::Basename;

if (@ARGV!=2) {die"Usage: *.pl <bam> <out>\n";exit;}
$dirname=dirname $ARGV[1];
system "mkdir -p $dirname";
open in,"samtools view -H $ARGV[0]|less -S |";
open out,">$ARGV[1]";
while (<in>) {
	chomp;
	if ($_=~/^\@RG/) {
		#@RG     ID:QFO7D.IonXpress_009  CN:TorrentServer/sn274670217    PU:PGM/318C/IonXpress_009       SM:c0012
		$need=(split/\:/,$_)[-1];
		print out "$need\n";
	}
}
close in;
close out;