#!/usr/bin/perl

use strict;
use warnings;

if(@ARGV < 1){
    print "Usage: perl $0 <seq>\n";
    exit;
}
open(S,"$ARGV[0]") or die "OpenError: $ARGV[0], $!\n";
#open(OUT,">$ARGV[1]") or die "OpenError: $ARGV[1], $!\n";
$/ = ">";
my ($name,$seq);
while (<S>) {
	chomp;
	next if(/^\s*$/);
	($name,$seq)=split(/\n/,$_,2);
    $name=~s/\r//g;
    $seq=uc($seq);
#	if ($name=~/\-/) {
		$seq=reverse $seq;
		$seq =~ tr/atgcATGC/tacgTACG/;
#		print "reverse $name\n";
#	}
	$seq=~s/[\n\r]//g;
	$seq=~s/(\w{50})/$1\n/g;
	print  ">$name\n$seq\n";

}

