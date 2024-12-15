package muia.tesis.map;
import muia.tesis.HighLevelGrammarParser;

import org.antlr.v4.runtime.Parser;
import org.antlr.v4.runtime.RecognitionException;
import org.antlr.v4.runtime.RuntimeMetaData;
import org.antlr.v4.runtime.TokenStream;
import org.antlr.v4.runtime.Vocabulary;
import org.antlr.v4.runtime.VocabularyImpl;
import org.antlr.v4.runtime.atn.ATN;
import org.antlr.v4.runtime.atn.ATNDeserializer;
import org.antlr.v4.runtime.atn.ParserATNSimulator;
import org.antlr.v4.runtime.atn.PredictionContextCache;
import org.antlr.v4.runtime.dfa.DFA;

public class HighLevelGrammarParser_v2 extends Parser {
   protected static final DFA[] _decisionToDFA;
   protected static final PredictionContextCache _sharedContextCache;
   public static final int T__0 = 1;
   public static final int T__1 = 2;
   public static final int NUM = 3;
   public static final int RULE_map = 0;
   public static final int RULE_connect = 1;
   public static final int RULE_contents = 2;
   public static final int RULE_cont = 3;
   public static final String[] ruleNames;
   private static final String[] _LITERAL_NAMES;
   private static final String[] _SYMBOLIC_NAMES;
   public static final Vocabulary VOCABULARY;
   /** @deprecated */
   @Deprecated
   public static final String[] tokenNames;
   public static final String _serializedATN = "\u0003\u0430\ud6d1\u8206\uad2d\u4417\uaef1\u8d80\uaadd\u0003\u0005$\u0004\u0002\t\u0002\u0004\u0003\t\u0003\u0004\u0004\t\u0004\u0004\u0005\t\u0005\u0003\u0002\u0003\u0002\u0003\u0002\u0003\u0002\u0003\u0002\u0003\u0002\u0003\u0002\u0003\u0002\u0007\u0002\u0013\n\u0002\f\u0002\u000e\u0002\u0016\u000b\u0002\u0003\u0003\u0003\u0003\u0003\u0004\u0003\u0004\u0003\u0004\u0007\u0004\u001d\n\u0004\f\u0004\u000e\u0004 \u000b\u0004\u0003\u0005\u0003\u0005\u0003\u0005\u0002\u0002\u0006\u0002\u0004\u0006\b\u0002\u0002!\u0002\n\u0003\u0002\u0002\u0002\u0004\u0017\u0003\u0002\u0002\u0002\u0006\u0019\u0003\u0002\u0002\u0002\b!\u0003\u0002\u0002\u0002\n\u000b\u0005\u0006\u0004\u0002\u000b\f\u0007\u0003\u0002\u0002\f\u0014\u0005\u0006\u0004\u0002\r\u000e\u0007\u0003\u0002\u0002\u000e\u000f\u0005\u0004\u0003\u0002\u000f\u0010\u0007\u0004\u0002\u0002\u0010\u0011\u0005\u0006\u0004\u0002\u0011\u0013\u0003\u0002\u0002\u0002\u0012\r\u0003\u0002\u0002\u0002\u0013\u0016\u0003\u0002\u0002\u0002\u0014\u0012\u0003\u0002\u0002\u0002\u0014\u0015\u0003\u0002\u0002\u0002\u0015\u0003\u0003\u0002\u0002\u0002\u0016\u0014\u0003\u0002\u0002\u0002\u0017\u0018\u0007\u0005\u0002\u0002\u0018\u0005\u0003\u0002\u0002\u0002\u0019\u001e\u0005\b\u0005\u0002\u001a\u001b\u0007\u0004\u0002\u0002\u001b\u001d\u0005\b\u0005\u0002\u001c\u001a\u0003\u0002\u0002\u0002\u001d \u0003\u0002\u0002\u0002\u001e\u001c\u0003\u0002\u0002\u0002\u001e\u001f\u0003\u0002\u0002\u0002\u001f\u0007\u0003\u0002\u0002\u0002 \u001e\u0003\u0002\u0002\u0002!\"\u0007\u0005\u0002\u0002\"\t\u0003\u0002\u0002\u0002\u0004\u0014\u001e";
   public static final ATN _ATN;

   /** @deprecated */
   @Deprecated
   public String[] getTokenNames() {
      return tokenNames;
   }

   public Vocabulary getVocabulary() {
      return VOCABULARY;
   }

   public String getGrammarFileName() {
      return "HighLevelGrammar.g4";
   }

   public String[] getRuleNames() {
      return ruleNames;
   }

   public String getSerializedATN() {
      return "\u0003\u0430\ud6d1\u8206\uad2d\u4417\uaef1\u8d80\uaadd\u0003\u0005$\u0004\u0002\t\u0002\u0004\u0003\t\u0003\u0004\u0004\t\u0004\u0004\u0005\t\u0005\u0003\u0002\u0003\u0002\u0003\u0002\u0003\u0002\u0003\u0002\u0003\u0002\u0003\u0002\u0003\u0002\u0007\u0002\u0013\n\u0002\f\u0002\u000e\u0002\u0016\u000b\u0002\u0003\u0003\u0003\u0003\u0003\u0004\u0003\u0004\u0003\u0004\u0007\u0004\u001d\n\u0004\f\u0004\u000e\u0004 \u000b\u0004\u0003\u0005\u0003\u0005\u0003\u0005\u0002\u0002\u0006\u0002\u0004\u0006\b\u0002\u0002!\u0002\n\u0003\u0002\u0002\u0002\u0004\u0017\u0003\u0002\u0002\u0002\u0006\u0019\u0003\u0002\u0002\u0002\b!\u0003\u0002\u0002\u0002\n\u000b\u0005\u0006\u0004\u0002\u000b\f\u0007\u0003\u0002\u0002\f\u0014\u0005\u0006\u0004\u0002\r\u000e\u0007\u0003\u0002\u0002\u000e\u000f\u0005\u0004\u0003\u0002\u000f\u0010\u0007\u0004\u0002\u0002\u0010\u0011\u0005\u0006\u0004\u0002\u0011\u0013\u0003\u0002\u0002\u0002\u0012\r\u0003\u0002\u0002\u0002\u0013\u0016\u0003\u0002\u0002\u0002\u0014\u0012\u0003\u0002\u0002\u0002\u0014\u0015\u0003\u0002\u0002\u0002\u0015\u0003\u0003\u0002\u0002\u0002\u0016\u0014\u0003\u0002\u0002\u0002\u0017\u0018\u0007\u0005\u0002\u0002\u0018\u0005\u0003\u0002\u0002\u0002\u0019\u001e\u0005\b\u0005\u0002\u001a\u001b\u0007\u0004\u0002\u0002\u001b\u001d\u0005\b\u0005\u0002\u001c\u001a\u0003\u0002\u0002\u0002\u001d \u0003\u0002\u0002\u0002\u001e\u001c\u0003\u0002\u0002\u0002\u001e\u001f\u0003\u0002\u0002\u0002\u001f\u0007\u0003\u0002\u0002\u0002 \u001e\u0003\u0002\u0002\u0002!\"\u0007\u0005\u0002\u0002\"\t\u0003\u0002\u0002\u0002\u0004\u0014\u001e";
   }

   public ATN getATN() {
      return _ATN;
   }

   public HighLevelGrammarParser_v2(TokenStream input) {
      super(input);
      this._interp = new ParserATNSimulator(this, _ATN, _decisionToDFA, _sharedContextCache);
   }

   public final HighLevelGrammarParser.MapContext map() throws RecognitionException {
      HighLevelGrammarParser.MapContext _localctx = new HighLevelGrammarParser.MapContext(this._ctx, this.getState());
      this.enterRule(_localctx, 0, 0);

      try {
         this.enterOuterAlt(_localctx, 1);
         this.setState(8);
         this.contents();

         this.setState(9);
         this.match(1);
         this.setState(10);
         this.contents();
         this.setState(16);
         this._errHandler.sync(this);

         for(int _la = this._input.LA(1); _la == 1; _la = this._input.LA(1)) {
            this.setState(11);
            this.match(1);
            this.setState(12);
            this.connect();
            this.setState(13);
            this.match(2);
            this.setState(14);
            this.contents();
            this.setState(20);
            this._errHandler.sync(this);
         }
      } catch (RecognitionException var7) {
         _localctx.exception = var7;
         //this._errHandler.reportError(this, var7);
         this._errHandler.recover(this, var7);
      } finally {
         this.exitRule();
      }

      return _localctx;
   }

   public final HighLevelGrammarParser.ConnectContext connect() throws RecognitionException {
      HighLevelGrammarParser.ConnectContext _localctx = new HighLevelGrammarParser.ConnectContext(this._ctx, this.getState());
      this.enterRule(_localctx, 2, 1);

      try {
         this.enterOuterAlt(_localctx, 1);
         this.setState(21);
         this.match(3);
      } catch (RecognitionException var6) {
         _localctx.exception = var6;
         this._errHandler.reportError(this, var6);
         this._errHandler.recover(this, var6);
      } finally {
         this.exitRule();
      }

      return _localctx;
   }

   public final HighLevelGrammarParser.ContentsContext contents() throws RecognitionException {
      HighLevelGrammarParser.ContentsContext _localctx = new HighLevelGrammarParser.ContentsContext(this._ctx, this.getState());
      this.enterRule(_localctx, 4, 2);

      try {
         this.enterOuterAlt(_localctx, 1);
         this.setState(23);
         this.cont();
         this.setState(28);
         this._errHandler.sync(this);

         for(int _la = this._input.LA(1); _la == 2; _la = this._input.LA(1)) {
            this.setState(24);
            this.match(2);
            this.setState(25);
            this.cont();
            this.setState(30);
            this._errHandler.sync(this);
         }
      } catch (RecognitionException var7) {
         _localctx.exception = var7;
         this._errHandler.reportError(this, var7);
         this._errHandler.recover(this, var7);
      } finally {
         this.exitRule();
      }

      return _localctx;
   }

   public final HighLevelGrammarParser.ContContext cont() throws RecognitionException {
      HighLevelGrammarParser.ContContext _localctx = new HighLevelGrammarParser.ContContext(this._ctx, this.getState());
      this.enterRule(_localctx, 6, 3);

      try {
         this.enterOuterAlt(_localctx, 1);
         this.setState(31);
         this.match(3);
      } catch (RecognitionException var6) {
         _localctx.exception = var6;
         this._errHandler.reportError(this, var6);
         this._errHandler.recover(this, var6);
      } finally {
         this.exitRule();
      }

      return _localctx;
   }

   static {
      RuntimeMetaData.checkVersion("4.5", "4.5");
      _sharedContextCache = new PredictionContextCache();
      ruleNames = new String[]{"map", "connect", "contents", "cont"};
      _LITERAL_NAMES = new String[]{null, "';'", "':'"};
      _SYMBOLIC_NAMES = new String[]{null, null, null, "NUM"};
      VOCABULARY = new VocabularyImpl(_LITERAL_NAMES, _SYMBOLIC_NAMES);
      tokenNames = new String[_SYMBOLIC_NAMES.length];

      int i;
      for(i = 0; i < tokenNames.length; ++i) {
         tokenNames[i] = VOCABULARY.getLiteralName(i);
         if (tokenNames[i] == null) {
            tokenNames[i] = VOCABULARY.getSymbolicName(i);
         }

         if (tokenNames[i] == null) {
            tokenNames[i] = "<INVALID>";
         }
      }

      _ATN = (new ATNDeserializer()).deserialize("\u0003\u0430\ud6d1\u8206\uad2d\u4417\uaef1\u8d80\uaadd\u0003\u0005$\u0004\u0002\t\u0002\u0004\u0003\t\u0003\u0004\u0004\t\u0004\u0004\u0005\t\u0005\u0003\u0002\u0003\u0002\u0003\u0002\u0003\u0002\u0003\u0002\u0003\u0002\u0003\u0002\u0003\u0002\u0007\u0002\u0013\n\u0002\f\u0002\u000e\u0002\u0016\u000b\u0002\u0003\u0003\u0003\u0003\u0003\u0004\u0003\u0004\u0003\u0004\u0007\u0004\u001d\n\u0004\f\u0004\u000e\u0004 \u000b\u0004\u0003\u0005\u0003\u0005\u0003\u0005\u0002\u0002\u0006\u0002\u0004\u0006\b\u0002\u0002!\u0002\n\u0003\u0002\u0002\u0002\u0004\u0017\u0003\u0002\u0002\u0002\u0006\u0019\u0003\u0002\u0002\u0002\b!\u0003\u0002\u0002\u0002\n\u000b\u0005\u0006\u0004\u0002\u000b\f\u0007\u0003\u0002\u0002\f\u0014\u0005\u0006\u0004\u0002\r\u000e\u0007\u0003\u0002\u0002\u000e\u000f\u0005\u0004\u0003\u0002\u000f\u0010\u0007\u0004\u0002\u0002\u0010\u0011\u0005\u0006\u0004\u0002\u0011\u0013\u0003\u0002\u0002\u0002\u0012\r\u0003\u0002\u0002\u0002\u0013\u0016\u0003\u0002\u0002\u0002\u0014\u0012\u0003\u0002\u0002\u0002\u0014\u0015\u0003\u0002\u0002\u0002\u0015\u0003\u0003\u0002\u0002\u0002\u0016\u0014\u0003\u0002\u0002\u0002\u0017\u0018\u0007\u0005\u0002\u0002\u0018\u0005\u0003\u0002\u0002\u0002\u0019\u001e\u0005\b\u0005\u0002\u001a\u001b\u0007\u0004\u0002\u0002\u001b\u001d\u0005\b\u0005\u0002\u001c\u001a\u0003\u0002\u0002\u0002\u001d \u0003\u0002\u0002\u0002\u001e\u001c\u0003\u0002\u0002\u0002\u001e\u001f\u0003\u0002\u0002\u0002\u001f\u0007\u0003\u0002\u0002\u0002 \u001e\u0003\u0002\u0002\u0002!\"\u0007\u0005\u0002\u0002\"\t\u0003\u0002\u0002\u0002\u0004\u0014\u001e".toCharArray());
      _decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];

      for(i = 0; i < _ATN.getNumberOfDecisions(); ++i) {
         _decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
      }

   }
}

