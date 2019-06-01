This program was designed for study purposes so 
no self-serving or trading intentions are not provided and highly disapproved
Ver. 1.0.0.
  This program is designated to solve TSP problem by means of elastic net method.

  Short description
	This program finds out one of the good
	(not nececcarily the best) ways between set points(e.g. cities)
	There's an option to change some values i.e. learning coefficients,
	total iterations quantity, screen refresh rate etc.

  Short instrucrion
	Nodes per city - amount of normalizing ring nodes dedicated for single point(city)

	Max iterations - maximal amount of iterations
 (if the limit reached, program will terminate regardless if algorithm either found the solution or not)

	Max Radius - number of nodes on each side of point is moved forward to the city which are decided to follow that one in gaussian progression

	Screen Refresh Rate - [times per second];it's believed there is no need to explain more what this parameter does

	Learning Rate Decrease speed - the higher the number the slower the learning rate will decrease
 KEEP IN MIND!!!! If it's high value would be combined with a large number of iterations, an error can occur and program will terminate within a few hundred of iterations

	Max Learning Rate - learning rate starts from it's value

	ComboBox - there's an opportunity to choose where the normalizing ring will be drawn from

	Write downn intermediate data to console - it prints all the data(step by step) if chosen
 PAY ATTENTION!!! There is no sense to choose this parameter if the program was ran not from .bat 'cause the visual console wasn't opened and the program just would be slowed down greatly writing the data to the virtual one

	Write to file - writes the result of current program into the file(see the 'output' folder) numerated from zero. Each next run with file writing will generate new file so there is no risk for the previous data to be lost

	Select file with data - this method helps to choose file with point's(city's) data on which the map generation will be based 
 You should know that even not any .tsp file could run. It's because some of them were made in incomprehensible for this program author way so make sure data is stored in way any of example data stored
 User wanting create their own map should use data.txt as a layout 
 Inner data format for .tsp files can be seen placing all the file's contents to Microsoft Word Document
-----------------------------------------------------------------------------

Ver 1.0.1

	-filled part of Instruction.docx
-----------------------------------------------------------------------------

Ver. 1.2.0

Renamed some parameters:
	- Max Radius -> Max Gaussian Radius

Added some controlling parameters:

	- Normalizing Ring Radius - defines radius of starting node's ring

	- Min Gaussian Radius - the minimal number of dots following the selected one in Gaussian progression
-----------------------------------------------------------------------------

Ver 1.3.0

Added (at this time nonfuntional fields:

	- cities Quantity
	- higher X Bound
	- higher Y Bound

	All the parameters listed above would be available for a random city generation function setup
	Their field's editability is controlled by checkbox:
		- generate cities Randomly;
-----------------------------------------------------------------------------

Ver 1.3.1

Minor improvements:

	-optimized spacing between fields
-----------------------------------------------------------------------------

Ver 1.3.4

GUI advance:

	-Form design was restructured and redesigned;
-----------------------------------------------------------------------------
    
Ver 1.3.5

Minor improvements:

	-form sizing was fixed;
-----------------------------------------------------------------------------

Ver 1.4.0

Major improvements:

	-added multilaunch feature

Minor improvements:

	-fixed some bugs with setting Min Gaussian Radius and it's processing
-----------------------------------------------------------------------------

Ver 1.5.1

Major improvements:

	-added feature of creating two additional frames with normalizing ring and absolute cities path separately

Minor improvements:

	- fixed problem with non-working Normalizing Ring Parameter
-----------------------------------------------------------------------------

Ver 1.5.2

	Minor bugs are fixed
-----------------------------------------------------------------------------

Ver 1.5.3

Major improvements:

	-bug with program freeze while deselecting checkbox 'generate Cities Randomly'
	
	-bug with problem freeze when parameters are started to set up from random generator parameters 

Minor improvements:

	-GUI code optimized

	-revalidating problem solved 
-----------------------------------------------------------------------------

Ver 1.6.0

New feature added:

	-setting up random cities by means of random city generator have been added
-----------------------------------------------------------------------------

Ver 1.6.2

Minor improvements:

	-bug when random city generator was not finding proper generated place was fixed
-----------------------------------------------------------------------------

Ver 1.6.3

New feature added:

	-now file generator writes it's files to 'generatedSources' folder
-----------------------------------------------------------------------------

Ver 1.6.4

Minor improvements:

	-bug with black frame instead of absolute pathway was fixed
-----------------------------------------------------------------------------

Ver 1.6.5

	'Інструкція.docx' file containing 'Instruction.docx''s contents translated into ukrainian was added
-----------------------------------------------------------------------------

Ver 1.6.6

	'Інструкція.docx' was greatly extended
-----------------------------------------------------------------------------

Ver 1.6.8

Minor improvements:

	- icons for both absolute path plot window and nodes path plot window were added
-----------------------------------------------------------------------------

Ver 1.6.9

Minor improvements:

	-minor bugs were fixed

	-some function and GUI code was optimized
-----------------------------------------------------------------------------

Ver 1.6.10

	All the program specification and working principles were described in 'Інструкція.docx'
-----------------------------------------------------------------------------

Ver 1.6.11

	New add-on: -the example of work session on one generated set of points was added.
			In this example all the functionality reliable work was demonstrated.

		    -for better comprehensibility screenshots were added and conclusion was formed
-----------------------------------------------------------------------------

Ver 1.6.12

	Hyperlinks on local files of input and output of the session were added 






For more detailed information of program work principles look the 'Instruction.docx' (on completion for now)
  	   Copyright: str1k6r(str1k6r2001@gmail.com)