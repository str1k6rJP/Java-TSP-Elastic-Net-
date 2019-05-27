This program was designed for study purposes so 
no self-serving or trading intentions are not provided and highly disapproved

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

    For more detailed information of program work principles look the 'Instruction.docx' (on completion for now)
  	   Copyright: str1k6r(str1k6r2001@gmail.com)