#!/usr/local/bin/perl -w


#use strict;
#use Getopt::Long;
#use Data::Dumper;
#########################################################################################
if (@ARGV<2){
	print "Usage: program seq start end\n";
	exit;
}
#open (L,"$ARGV[0]")||die"Can't open $ARGV[0]\n";
open (S,"$ARGV[0]")||die"Can't open $ARGV[1]\n";
#open (O,">$ARGV[2]")||die"Can't open $ARGV[2]\n";
#$od=$ARGV[2];
#$od=~s/\/$//;
#########################################################################################
#while (<L>) {#Contig000366	9430	9577
#	chomp;
#	@a=split;
#	$name=$a[0];
#	if ($name=~/\:/) {
#		$name=(split(/\:/,$name))[1];
#	}
#	elsif ($name=~/\|/) {
#		$name=(split(/\|/,$name))[3];
#	}
#	else {
#		$name=$name;
#	}
#	$length=abs($a[2]-$a[1])+1;
#	$min=&min($a[1],$a[2]);
#	push (@{$hash_s{$name}},$min);
#	push (@{$hash_l{$name}},$length);
#}
#close L;
#
$length=abs($ARGV[2]-$ARGV[1])+1;
$left=&min($ARGV[1],$ARGV[2]);


$/=">";
while (<S>) {
	chomp;
	if ($_ ne "") {
		$name=(split(/\s+/,$_))[0];
		if ($name=~/\:/) {
			$name=(split(/\:/,$name))[1];
		}
		elsif ($name=~/\|/) {
			$name=(split(/\|/,$name))[3];
		}
		else {
			$name=$name;
		}
#		if (exists $hash_s{$name}) {
			$seq=(split(/\n/,$_,2))[1];
			$seq=~s/\n//g;
#			for($i=0 ;$i<@{$hash_s{$name}} ;$i++) {
#				$left=${$hash_s{$name}}[$i];
#				$length=${$hash_l{$name}}[$i];
				$right=$left+$length-1;
				$file=$name."_".$left."_".$right;
				$new_seq=substr($seq,$left-1,$length);
				$new_seq=~s/(.{50})/$1\n/g;
				$new_seq=~s/\n$//;
#				open (O,">$od/$file.seq");
				print  ">$file\n$new_seq\n";
#				print O "
#			}
#		}
	}
}
close S;

sub min {
	my ($x1,$x2)=@_;
	my $min;
	if ($x1 < $x2) {
		$min=$x1;
	}
	else {
		$min=$x2;
	}
	return $min;
}
