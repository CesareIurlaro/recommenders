package org.eclipse.recommenders.tests.extdoc.rcp.selection2;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import junit.framework.Assert;
import org.eclipse.core.resources.IWorkspace;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.jdt.core.IJavaElement;
import org.eclipse.jdt.core.dom.ASTNode;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jdt.core.dom.IBinding;
import org.eclipse.jdt.core.dom.NodeFinder;
import org.eclipse.jdt.core.dom.SimpleName;
import org.eclipse.recommenders.tests.extdoc.rcp.selection2.XtendUtils;
import org.eclipse.recommenders.tests.jdt.JavaProjectFixture;
import org.eclipse.recommenders.utils.Tuple;
import org.eclipse.xtext.xbase.lib.CollectionLiterals;
import org.eclipse.xtext.xbase.lib.ObjectExtensions;
import org.eclipse.xtext.xbase.lib.Pair;
import org.eclipse.xtext.xtend2.lib.StringConcatenation;
import org.junit.Test;

@SuppressWarnings("all")
public class JavaElementSelectionTest {
  @Test
  public void testTypeSelectionsInMethodBody() {
      StringConcatenation _builder = new StringConcatenation();
      _builder.append("class Myclass {");
      _builder.newLine();
      _builder.append("\t");
      _builder.append("Str\u2022ing s = new St\u2022ring(\"\");");
      _builder.newLine();
      _builder.append("}");
      final StringConcatenation code = _builder;
      Pair<String,Integer> _operator_mappedTo = ObjectExtensions.<String, Integer>operator_mappedTo("Ljava/lang/String;", ((Integer)2));
      List<String> _newListWithFrequency = XtendUtils.<String>newListWithFrequency(_operator_mappedTo);
      final List<String> expected = _newListWithFrequency;
      this.exerciseAndVerify(code, expected);
  }
  
  @Test
  public void testTypeSelectionInTypeDeclaration() {
      StringConcatenation _builder = new StringConcatenation();
      _builder.append("class Myc\u2022lass {}");
      final StringConcatenation code = _builder;
      Pair<String,Integer> _operator_mappedTo = ObjectExtensions.<String, Integer>operator_mappedTo(((String) null), ((Integer)1));
      List<String> _newListWithFrequency = XtendUtils.<String>newListWithFrequency(_operator_mappedTo);
      final List<String> expected = _newListWithFrequency;
      this.exerciseAndVerify(code, expected);
  }
  
  @Test
  public void testTypeSelectionInExtends() {
      StringConcatenation _builder = new StringConcatenation();
      _builder.append("import java.util.*;");
      _builder.newLine();
      _builder.append("class Myclass extends L\u2022ist {}");
      _builder.newLine();
      final StringConcatenation code = _builder;
      Pair<String,Integer> _operator_mappedTo = ObjectExtensions.<String, Integer>operator_mappedTo("Ljava/util/List<>;", ((Integer)1));
      List<String> _newListWithFrequency = XtendUtils.<String>newListWithFrequency(_operator_mappedTo);
      final List<String> expected = _newListWithFrequency;
      this.exerciseAndVerify(code, expected);
  }
  
  @Test
  public void testTypeSelectionInMethodBody() {
      StringConcatenation _builder = new StringConcatenation();
      _builder.append("class Myclass {");
      _builder.newLine();
      _builder.append("\t");
      _builder.append("Str\u2022ing s = new St\u2022ring(\"\");");
      _builder.newLine();
      _builder.append("}");
      final StringConcatenation code = _builder;
      Pair<String,Integer> _operator_mappedTo = ObjectExtensions.<String, Integer>operator_mappedTo("Ljava/lang/String;", ((Integer)2));
      List<String> _newListWithFrequency = XtendUtils.<String>newListWithFrequency(_operator_mappedTo);
      final List<String> expected = _newListWithFrequency;
      this.exerciseAndVerify(code, expected);
  }
  
  @Test
  public void testMethodSelectionInMethodBody() {
      StringConcatenation _builder = new StringConcatenation();
      _builder.append("class Myclass {");
      _builder.newLine();
      _builder.append("\t");
      _builder.append("void test(String s1){");
      _builder.newLine();
      _builder.append("\t\t");
      _builder.append("String s2 = s1.co\u2022ncat(\"hello\");");
      _builder.newLine();
      _builder.append("\t\t");
      _builder.append("s2.hashCode\u2022();");
      _builder.newLine();
      _builder.append("\t\t");
      _builder.append("s1.\u2022equals(s2);");
      _builder.newLine();
      _builder.append("\t");
      _builder.append("}");
      _builder.newLine();
      _builder.append("\t");
      _builder.newLine();
      _builder.append("}");
      final StringConcatenation code = _builder;
      Pair<String,Integer> _operator_mappedTo = ObjectExtensions.<String, Integer>operator_mappedTo("Ljava/lang/String;.concat(Ljava/lang/String;)Ljava/lang/String;", ((Integer)1));
      Pair<String,Integer> _operator_mappedTo_1 = ObjectExtensions.<String, Integer>operator_mappedTo("Ljava/lang/String;.hashCode()I", ((Integer)1));
      Pair<String,Integer> _operator_mappedTo_2 = ObjectExtensions.<String, Integer>operator_mappedTo("Ljava/lang/String;.equals(Ljava/lang/Object;)Z", ((Integer)1));
      List<String> _newListWithFrequency = XtendUtils.<String>newListWithFrequency(_operator_mappedTo, _operator_mappedTo_1, _operator_mappedTo_2);
      final List<String> expected = _newListWithFrequency;
      this.exerciseAndVerify(code, expected);
  }
  
  public void exerciseAndVerify(final StringConcatenation code, final List<String> expected) {
      IWorkspace _workspace = ResourcesPlugin.getWorkspace();
      JavaProjectFixture _javaProjectFixture = new JavaProjectFixture(_workspace, "test");
      final JavaProjectFixture fixture = _javaProjectFixture;
      String _string = code.toString();
      Tuple<CompilationUnit,Set<Integer>> _parseWithMarkers = fixture.parseWithMarkers(_string, "MyClass.java");
      final Tuple<CompilationUnit,Set<Integer>> struct = _parseWithMarkers;
      CompilationUnit _first = struct.getFirst();
      final CompilationUnit cu = _first;
      Set<Integer> _second = struct.getSecond();
      final Set<Integer> pos = _second;
      ArrayList<String> _newArrayList = CollectionLiterals.<String>newArrayList();
      final List<String> actual = _newArrayList;
      for (final Integer position : pos) {
        {
          ASTNode _perform = NodeFinder.perform(cu, position, 0);
          final ASTNode selection = _perform;
          final ASTNode selection_1 = selection;
          boolean matched = false;
          if (!matched) {
            if (selection_1 instanceof SimpleName) {
              final SimpleName selection_2 = (SimpleName) selection_1;
              matched=true;
              {
                IBinding _resolveBinding = selection_2.resolveBinding();
                final IBinding binding = _resolveBinding;
                IJavaElement _javaElement = binding.getJavaElement();
                final IJavaElement javaElement = _javaElement;
                boolean _operator_equals = ObjectExtensions.operator_equals(javaElement, null);
                if (_operator_equals) {
                  actual.add(null);
                } else {
                  String _key = binding.getKey();
                  actual.add(_key);
                }
              }
            }
          }
        }
      }
      Assert.assertEquals(expected, actual);
  }
}
