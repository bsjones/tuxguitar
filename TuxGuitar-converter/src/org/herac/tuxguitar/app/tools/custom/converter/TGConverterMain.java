package org.herac.tuxguitar.app.tools.custom.converter;

import org.herac.tuxguitar.util.TGContext;
import org.herac.tuxguitar.event.TGEvent;
import org.herac.tuxguitar.event.TGEventListener;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.Option.Builder;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.ParseException;

public class TGConverterMain implements TGConverterListener, TGEventListener{
  private TGContext context;
  String inputType;
  String outputType;
  String inputFile;
  String outputFile;

  public TGConverterMain(String[] args)
  {
    this.context = new TGContext();
    parseArgs(args);
  }

  private void parseArgs(String[] args)
  {
    System.out.print(args.length);
    if (args.length != 6 )
    {
      printHelp();
      System.exit(0);
    }

    CommandLine commandLine;
    Option option_in = Option.builder("i")
      .required(true)
      .hasArg()
      .desc("Input file type")
      .longOpt("in")
      .build();
    Option option_out = Option.builder("o")
      .required(true)
      .hasArg()
      .desc("Output file type")
      .longOpt("out")
      .build();

    Options options = new Options();
    CommandLineParser parser = new DefaultParser();

    options.addOption(option_in);
    options.addOption(option_out);


    try
    {
      commandLine = parser.parse(options, args);

      if (commandLine.hasOption("i"))
      {
        inputType = commandLine.getOptionValue("i");
      }
      if (commandLine.hasOption("o"))
      {
        outputType = commandLine.getOptionValue("o");
      }

      {
        String[] remainder = commandLine.getArgs();
        System.out.println("Remaining arguments: ");
        if ( remainder.length != 2 )
        {
          System.out.println( remainder.length);
          printHelp();
          System.exit(1);
        }
        inputFile = remainder[0];
        outputFile = remainder[1];
      }
    }
    catch (ParseException exception)
    {
      System.out.print("Parse error: ");
      System.out.println(exception.getMessage());
    }
  }


  public void convert()
  {
    TGConverter converter= new TGConverter(context, inputFile, outputFile);
    converter.setListener(this);
    System.out.println("Converting input " + inputFile + " to " + outputFile);
    converter.convert(inputFile, outputFile);
  }

  private void printHelp()
  {
    System.out.print("Usage: TGConverterMain -i input_type -o output_type input_path output_path");
  }

  //------------------------------------------------------------------------------------------------//
  //---TGConverterListener Implementation ----------------------------------------------------------//
  //------------------------------------------------------------------------------------------------//

  public void notifyFileProcess(final String filename) {
  }

  public void notifyFileResult(final String filename, final int result) {
    System.out.println( filename + " Result " + result );
  }

  public void notifyStart() {
    System.out.println( "Starting..." );
  }

  public void notifyFinish() {
    System.out.println( "Finished" );
  }

  public static void main(String[] args)
  {
    TGConverterMain conv = new TGConverterMain(args);
    try
    {
      conv.convert();
    }
    catch (Exception exception)
    {
      System.out.print("Error converting: ");
      System.out.println(exception.getMessage());
      exception.printStackTrace(System.out);
    }
  }

	public void processEvent(TGEvent event) {

	}
}