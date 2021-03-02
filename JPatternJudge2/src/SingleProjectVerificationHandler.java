import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.IPath;
import org.eclipse.jdt.core.IJavaElement;
import org.eclipse.jdt.core.IJavaProject;
import org.eclipse.jdt.internal.core.PackageFragmentRoot;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.ISelectionService;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.internal.Workbench;

import designPatterns.DesignPatternRegistry;
import detectionVerification.DesignPatternStorage;
import detectionVerification.PatternDetector;
import detectionVerification.PatternVerifier;
public class SingleProjectVerificationHandler extends AbstractHandler {

	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException {
		//System.out.println("BEGINNING COMMAND");
		
		IProject project = null;
	    IWorkbenchWindow window = PlatformUI.getWorkbench().getActiveWorkbenchWindow();
	    /*
	    IWorkbenchPage activePage = window.getActivePage();

	    IEditorPart activeEditor = activePage.getActiveEditor();

	    if (activeEditor != null) {
	       IEditorInput input = activeEditor.getEditorInput();

	       project = input.getAdapter(IProject.class);
	       if (project == null) {
	          IResource resource = input.getAdapter(IResource.class);
	          if (resource != null) {
	             project = resource.getProject();
	          }
	       }
	    }
	    */
		ISelectionService selectionService =     
	            Workbench.getInstance().getActiveWorkbenchWindow().getSelectionService();    

	        ISelection selection = selectionService.getSelection();    
  
	        if(selection instanceof IStructuredSelection) {    
	            Object element = ((IStructuredSelection)selection).getFirstElement();    

	            if (element instanceof IResource) {    
	                project= ((IResource)element).getProject();    
	            } else if (element instanceof PackageFragmentRoot) {    
	                IJavaProject jProject =     
	                    ((PackageFragmentRoot)element).getJavaProject();    
	                project = jProject.getProject();    
	            } else if (element instanceof IJavaElement) {    
	                IJavaProject jProject= ((IJavaElement)element).getJavaProject();    
	                project = jProject.getProject();    
	            }    
	        }   
	    if (project !=null)
	    {
	    	PatternDetector detector = new PatternDetector();
	 	    DesignPatternStorage storage = detector.detectPatterns(project);
	 	    PatternVerifier verifier = new PatternVerifier();
	 	    verifier.verifyPatterns(storage);	
	 	    
	 	    
	 	    
	 	    String filename = project.getLocation().toString() + "/DesignPatternReport.txt";
	 	    File reportFile = new File(filename);
	 	    try {
				FileWriter writer = new FileWriter(reportFile);
				
				//String debugMessage = detector.getDebugMessage();
				//debugMessage += detector.getNumberOfPatterns() + " " + detector.getNumberOfRoles() + "\n";
				//writer.write(debugMessage);
				
				writer.write(verifier.getMessage());
				writer.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
	 	    String title = project.getName()+ " Results";
	 		MessageDialog.openInformation(
	 					window.getShell(),
	 					title,
	 					verifier.getMessage());
	    }
	   

		return null;
	}

	


}
